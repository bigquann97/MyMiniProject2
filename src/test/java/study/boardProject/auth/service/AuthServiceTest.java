package study.boardProject.auth.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.boardProject.auth.dto.LoginRequest;
import study.boardProject.auth.dto.SignupRequest;
import study.boardProject.auth.dto.TokenDto;
import study.boardProject.auth.dto.TokenRequest;
import study.boardProject.auth.entity.RefreshToken;
import study.boardProject.auth.entity.User;
import study.boardProject.auth.entity.UserRole;
import study.boardProject.auth.repository.RefreshTokenRepository;
import study.boardProject.auth.repository.UserRepository;
import study.boardProject.common.auth.AuthClaims;
import study.boardProject.common.auth.AuthDto;
import study.boardProject.common.auth.AuthUtil;
import study.boardProject.factory.RefreshTokenFactory;
import study.boardProject.factory.UserFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static study.boardProject.factory.RefreshTokenFactory.*;
import static study.boardProject.factory.UserFactory.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    RefreshTokenRepository refreshTokenRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    AuthUtil authUtil;

    AuthService authService;

    @BeforeEach
    void before() {
        authService = new AuthServiceImpl(refreshTokenRepository, userRepository, passwordEncoder, authUtil);
    }


    @DisplayName("1. 정상 회원가입")
    @Test
    void test_1() {
        //given
        SignupRequest req = new SignupRequest("testid", "testpw", "test", "test", "test@email.com", 30);

        //when
        authService.signup(req);

        //then
        verify(userRepository).existsByLoginId(req.getLoginId());
        verify(userRepository).existsByNickname(req.getNickname());
        verify(userRepository).existsByEmail(req.getEmail());
        verify(passwordEncoder).encode(req.getLoginPw());
        verify(userRepository).save(any());
    }

    @DisplayName("2. 정상 로그인")
    @Test
    void test_2() {
        //given
        LoginRequest req = new LoginRequest("testid", "testpw");
        MockHttpServletResponse res = new MockHttpServletResponse();

        when(userRepository.findByLoginId(req.getLoginId())).thenReturn(Optional.ofNullable(buildUser1()));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(authUtil.generateAuthDto(any())).thenReturn(new TokenDto("grantType", "access", "refresh", 100L));
        when(authUtil.createAuthentication(any())).thenReturn(new AnonymousAuthenticationToken("key", "principal", List.of(new SimpleGrantedAuthority("role"))));

        //then
        authService.login(req, res);

        //then
        verify(passwordEncoder).matches(any(), any());
        verify(refreshTokenRepository).save(any());
    }

    @DisplayName("3. 정상 토큰 재발급")
    @Test
    void test_3 () {
        //given
        TokenRequest req = new TokenRequest("access", "refresh");
        MockHttpServletResponse res = new MockHttpServletResponse();

        when(authUtil.validateAuthTool(any())).thenReturn(true);
        when(authUtil.parseAuthClaims(any())).thenReturn(new AuthClaims());
        when(refreshTokenRepository.findByEmail(any())).thenReturn(Optional.of(buildRefreshToken()));
        when(authUtil.generateAuthDto(any())).thenReturn(new TokenDto("grant", "access", "refresh", 100L));

        //when
        authService.reissue(req, res);

        //then
        verify(authUtil).validateAuthTool(req.getRefreshToken());
        verify(authUtil).parseAuthClaims(req.getAccessToken());
        verify(refreshTokenRepository).findByEmail(any());
        verify(refreshTokenRepository).save(any());
    }

}