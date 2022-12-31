package sparta.spartaproject.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.spartaproject.config.jwt.JwtUtil;
import sparta.spartaproject.dto.token.TokenDto;
import sparta.spartaproject.dto.token.TokenRequest;
import sparta.spartaproject.dto.token.TokenResponse;
import sparta.spartaproject.dto.user.LoginRequest;
import sparta.spartaproject.dto.user.SignupRequest;
import sparta.spartaproject.entity.RefreshToken;
import sparta.spartaproject.entity.User;
import sparta.spartaproject.exception.SignException;
import sparta.spartaproject.repository.RefreshTokenRepository;
import sparta.spartaproject.repository.UserRepository;

import javax.servlet.http.HttpServletResponse;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil tokenProvider;

    @Transactional
    public void signup(SignupRequest signupRequest) {
        validateSignupReq(signupRequest);
        String encodedPw = passwordEncoder.encode(signupRequest.getLoginPw());
        User user = signupRequest.toEntity(encodedPw);
        userRepository.save(user);
    }

    @Transactional
    public void login(LoginRequest loginRequest, HttpServletResponse response) {
        User user = userRepository.findUserByLoginId(loginRequest.getLoginId()).orElseThrow(IllegalArgumentException::new);
        validatePw(user, loginRequest);

        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        response.addHeader("Authorization", tokenDto.getAccessToken());
    }

    @Override
    @Transactional
    public TokenResponse reissue(TokenRequest tokenRequestDto) {
        validateRefreshToken(tokenRequestDto);

        Authentication authentication = tokenProvider.createAuthentication(tokenRequestDto.getAccessToken());
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));
        validateRefreshTokenOwner(refreshToken, tokenRequestDto);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        TokenResponse tokenResponse = new TokenResponse(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
        return tokenResponse;
    }


    void validateSignupReq(SignupRequest signupRequest) {
        if (!signupRequest.validatePw())
            throw new SignException();
        else if (userRepository.existsByLoginId(signupRequest.getLoginId()))
            throw new SignException();
    }

    private void validatePw(User user, LoginRequest loginRequest) {
        if(!passwordEncoder.matches(loginRequest.getLoginPw(), user.getLoginPw()))
            throw new SignException();
    }

    private void validateRefreshToken(TokenRequest tokenRequestDto) {
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }
    }

    private void validateRefreshTokenOwner(RefreshToken refreshToken, TokenRequest tokenRequestDto) {
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }
    }

}
