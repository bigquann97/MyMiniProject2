package sparta.spartaproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.spartaproject.config.jwt.TokenProvider;
import sparta.spartaproject.dto.token.TokenDto;
import sparta.spartaproject.dto.token.TokenResponse;
import sparta.spartaproject.dto.user.LoginRequest;
import sparta.spartaproject.dto.user.SignupRequest;
import sparta.spartaproject.dto.user.SignupResponse;
import sparta.spartaproject.entity.RefreshToken;
import sparta.spartaproject.entity.User;
import sparta.spartaproject.exception.AlreadyExistUserException;
import sparta.spartaproject.exception.NotExistUserException;
import sparta.spartaproject.exception.PwNotMatchException;
import sparta.spartaproject.exception.WrongPwException;
import sparta.spartaproject.repository.RefreshTokenRepository;
import sparta.spartaproject.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {
        validateSignupReq(signupRequest);
        String encodedPw = passwordEncoder.encode(signupRequest.getLoginPw());
        User user = signupRequest.toEntity(encodedPw);
        userRepository.save(user);
        return SignupResponse.of(user);
    }

    @Transactional
    public TokenResponse login(LoginRequest loginRequest) {
        User user = userRepository.findUserByLoginId(loginRequest.getLoginId()).orElseThrow(NotExistUserException::new);
        validatePw(user, loginRequest);

        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        return TokenResponse.of(tokenDto);
    }

    void validateSignupReq(SignupRequest signupRequest) {
        if (!signupRequest.validatePw())
            throw new PwNotMatchException();
        else if (userRepository.existsByLoginId(signupRequest.getLoginId()))
            throw new AlreadyExistUserException();
    }

    private void validatePw(User user, LoginRequest loginRequest) {
        if(! passwordEncoder.matches(loginRequest.getLoginPw(), user.getLoginPw()))
            throw new WrongPwException();
    }

}
