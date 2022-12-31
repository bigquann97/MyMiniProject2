package temp.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sparta.spartaproject.config.jwt.JwtUtil;
import sparta.spartaproject.dto.user.LoginRequest;
import sparta.spartaproject.dto.user.SignupRequest;
import sparta.spartaproject.entity.User;
import sparta.spartaproject.exception.MismatchException;
import sparta.spartaproject.exception.SignException;
import sparta.spartaproject.factory.UserFactory;
import sparta.spartaproject.repository.RefreshTokenRepository;
import sparta.spartaproject.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    UserServiceImpl userServiceImpl;
    @Mock
    RefreshTokenRepository refreshTokenRepository;
    @Mock
    AuthenticationManagerBuilder authenticationManagerBuilder;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtUtil tokenProvider;

    @BeforeEach
    void beforeEach() {
        userServiceImpl = new UserServiceImpl(refreshTokenRepository, authenticationManagerBuilder, userRepository, passwordEncoder, tokenProvider);
    }

    @DisplayName("1. 회원가입 정상 로직 테스트")
    @Test
    void test_1 () {
        SignupRequest req = SignupRequest.builder()
                .email("temp@naver.com")
                .wantAdmin(false)
                .adminKey("makeMeAdmin")
                .loginPw("temp1234")
                .loginPwAgain("temp1234")
                .loginId("temp1234")
                .name("임시이름")
                .age(26)
                .build();

        // when
        userServiceImpl.signup(req);

        // then
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode(req.getLoginPw());
    }

    @DisplayName("2. 어드민 키 불일치 오류")
    @Test
    void test_2() {
        SignupRequest req = SignupRequest.builder()
                .email("temp@naver.com")
                .wantAdmin(true)
                .adminKey("wrongKey")
                .loginPw("temp1234")
                .loginPwAgain("temp1234")
                .loginId("temp1234")
                .name("임시이름")
                .age(26)
                .build();

        assertThatThrownBy(() -> userServiceImpl.signup(req))
                .isInstanceOf(MismatchException.class);
    }

    @DisplayName("3. 비밀번호, 재입력 비밀번호 불일치 오류")
    @Test
    void test_3 () {
        SignupRequest req = SignupRequest.builder()
                .email("temp@naver.com")
                .wantAdmin(false)
                .adminKey("wrongKey")
                .loginPw("temp1234")
                .loginPwAgain("notSame")
                .loginId("temp1234")
                .name("임시이름")
                .age(26)
                .build();

        assertThatThrownBy(() -> userServiceImpl.signup(req))
                .isInstanceOf(SignException.class);
    }

    @DisplayName("4. 이미 존재하는 아이디 오류")
    @Test
    void test_4() {
        SignupRequest req = UserFactory.createSignupReq();
        given(userRepository.existsByLoginId("temp")).willReturn(true);

        assertThatThrownBy(() -> userServiceImpl.signup(req))
                .isInstanceOf(AlreadyExistUserException.class);
    }

    @DisplayName("5. 존재하지 않는 아이디 로그인 오류")
    @Test
    void test_5 () {
        LoginRequest req = LoginRequest.builder()
                .loginId("temp")
                .loginPw("temp")
                .build();

    }

    @DisplayName("6. 정상 로그인 테스트")
    @Test
    void test_6() {
        // TODO: 2022/12/14 어떻게 해야하나 ..
    }
}