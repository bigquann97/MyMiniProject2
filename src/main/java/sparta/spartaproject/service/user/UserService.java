package sparta.spartaproject.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.spartaproject.config.jwt.TokenProvider;
import sparta.spartaproject.dto.token.TokenDto;
import sparta.spartaproject.entity.user.RefreshToken;
import sparta.spartaproject.entity.user.User;
import sparta.spartaproject.exception.AlreadyExistUserException;
import sparta.spartaproject.exception.NotExistUserException;
import sparta.spartaproject.exception.PwNotMatchException;
import sparta.spartaproject.exception.WrongPwException;
import sparta.spartaproject.repository.refreshToken.RefreshTokenRepository;
import sparta.spartaproject.repository.user.UserRepository;

import static sparta.spartaproject.dto.token.TokenDto.TokenRes;
import static sparta.spartaproject.dto.user.UserDto.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public SignupRes signup(SignUpReq signUpReq) {
        validateSignupReq(signUpReq);
        String encodedPw = passwordEncoder.encode(signUpReq.getLoginPw());
        User user = SignUpReq.toEntity(signUpReq, encodedPw);
        userRepository.save(user);
        return SignupRes.of(user);
    }

    @Transactional
    public TokenRes login(LoginReq loginReq) {
        User user = userRepository.findUserByLoginId(loginReq.getLoginId()).orElseThrow(NotExistUserException::new);
        validatePw(user, loginReq);

        UsernamePasswordAuthenticationToken authenticationToken = loginReq.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        return TokenRes.of(tokenDto);
    }

    void validateSignupReq(SignUpReq signUpReq) {
        if (!signUpReq.validatePw())
            throw new PwNotMatchException();
        else if (userRepository.existsByLoginId(signUpReq.getLoginId()))
            throw new AlreadyExistUserException();
    }

    private void validatePw(User user, LoginReq loginReq) {
        if(! passwordEncoder.matches(loginReq.getLoginPw(), user.getLoginPw()))
            throw new WrongPwException();
    }

}
