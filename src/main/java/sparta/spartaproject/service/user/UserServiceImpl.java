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

}
