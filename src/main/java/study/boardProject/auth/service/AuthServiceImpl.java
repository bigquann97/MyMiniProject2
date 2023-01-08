package study.boardProject.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.boardProject.auth.dto.*;
import study.boardProject.auth.entity.RefreshToken;
import study.boardProject.auth.entity.User;
import study.boardProject.auth.repository.RefreshTokenRepository;
import study.boardProject.auth.repository.UserRepository;
import study.boardProject.common.auth.AuthDto;
import study.boardProject.common.auth.AuthUtil;
import study.boardProject.common.exception.AuthException;
import study.boardProject.common.exception.TokenException;

import javax.servlet.http.HttpServletResponse;

import static study.boardProject.common.exception.AuthException.*;
import static study.boardProject.common.exception.TokenException.*;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtil authUtil;

    @Transactional
    public void signup(SignupRequest signupRequest) {
        validateSignupReq(signupRequest);
        String encodedPw = passwordEncoder.encode(signupRequest.getLoginPw());
        User user = signupRequest.toEntity(encodedPw);
        userRepository.save(user);
    }

    @Transactional
    public TokenResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        User user = userRepository.findUserByLoginId(loginRequest.getLoginId()).orElseThrow(UserNotFoundException::new);
        validatePw(user, loginRequest);

        Authentication authentication = authUtil.createAuthentication(user.getLoginId());
        AuthDto authDto = authUtil.generateAuthDto(authentication);

        if (authDto instanceof TokenDto) {
            TokenDto tokenDto = (TokenDto) authDto;

            RefreshToken refreshToken = RefreshToken.builder()
                    .key(authentication.getName())
                    .value(tokenDto.getRefreshToken())
                    .build();

            refreshTokenRepository.save(refreshToken);

            return TokenResponse.builder()
                    .accessToken(tokenDto.getAccessToken())
                    .refreshToken(tokenDto.getRefreshToken())
                    .build();
        }

        throw new AuthException();
    }

    @Override
    @Transactional
    public TokenResponse reissue(TokenRequest tokenRequest) {
        validateRefreshToken(tokenRequest);
        String loginId = authUtil.parseAuthClaims(tokenRequest.getRefreshToken()).getSubject();

        Authentication authentication = authUtil.createAuthentication(loginId);
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName()).orElseThrow(AlreadyLogoutException::new);

        validateRefreshTokenOwner(refreshToken, tokenRequest);

        AuthDto authDto = authUtil.generateAuthDto(authentication);

        if (authDto instanceof TokenDto) {
            TokenDto tokenDto = (TokenDto) authDto;
            RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
            refreshTokenRepository.save(newRefreshToken);

            return TokenResponse.builder()
                    .accessToken(tokenDto.getAccessToken())
                    .refreshToken(tokenDto.getRefreshToken())
                    .build();
        }

        throw new TokenException();
    }


    void validateSignupReq(SignupRequest signupRequest) {
        if (userRepository.existsByLoginId(signupRequest.getLoginId()))
            throw new DuplicatedLoginIdException();
        else if (userRepository.existsByNickname(signupRequest.getNickname()))
            throw new AuthException.DuplicatedNicknameException();
    }

    private void validatePw(User user, LoginRequest loginRequest) {
        if(!passwordEncoder.matches(loginRequest.getLoginPw(), user.getLoginPw()))
            throw new PasswordNotCorrectException();
    }

    private void validateRefreshToken(TokenRequest tokenRequestDto) {
        if (!authUtil.validateAuthTool(tokenRequestDto.getRefreshToken())) {
            throw new InvalidRefreshTokenException();
        }
    }

    private void validateRefreshTokenOwner(RefreshToken refreshToken, TokenRequest tokenRequestDto) {
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new TokenOwnerNotMatchException();
        }
    }

}
