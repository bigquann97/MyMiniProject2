package study.boardProject.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.boardProject.common.auth.AuthDto;
import study.boardProject.common.jwt.JwtUtil;
import study.boardProject.auth.dto.TokenDto;
import study.boardProject.auth.dto.TokenRequest;
import study.boardProject.auth.dto.TokenResponse;
import study.boardProject.auth.dto.LoginRequest;
import study.boardProject.auth.dto.SignupRequest;
import study.boardProject.auth.entity.RefreshToken;
import study.boardProject.auth.entity.User;
import study.boardProject.common.exception.SignException;
import study.boardProject.auth.repository.RefreshTokenRepository;
import study.boardProject.auth.repository.UserRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

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
    public Map<String, String> login(LoginRequest loginRequest, HttpServletResponse response) {
        User user = userRepository.findUserByLoginId(loginRequest.getLoginId()).orElseThrow(IllegalArgumentException::new);
        validatePw(user, loginRequest);

        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        AuthDto authDto = tokenProvider.generateAuthDto(authentication);
        TokenDto tokenDto = null;

        if (authDto instanceof TokenDto) {
            tokenDto = (TokenDto) authDto;
        }

        assert tokenDto != null;
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        Map<String, String> result = new HashMap<>();
        result.put("Authorization", "Bearer " + tokenDto.getAccessToken());
        return result;
    }

    @Override
    @Transactional
    public TokenResponse reissue(TokenRequest tokenRequestDto) {
        validateRefreshToken(tokenRequestDto);

        Authentication authentication = tokenProvider.createAuthentication(tokenRequestDto.getAccessToken());
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));
        validateRefreshTokenOwner(refreshToken, tokenRequestDto);

        AuthDto authDto = tokenProvider.generateAuthDto(authentication);
        TokenDto tokenDto = null;

        if (authDto instanceof TokenDto) {
            tokenDto = (TokenDto) authDto;
        }

        assert tokenDto != null;
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return new TokenResponse(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
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
        if (!tokenProvider.validateAuthTool(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }
    }

    private void validateRefreshTokenOwner(RefreshToken refreshToken, TokenRequest tokenRequestDto) {
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }
    }

}
