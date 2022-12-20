package sparta.spartaproject.factory;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sparta.spartaproject.dto.user.SignupRequest;
import sparta.spartaproject.entity.User;
import sparta.spartaproject.entity.UserRole;

public class UserFactory {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static User createAdminUser() {
        return User.builder()
                .name("르탄운영자")
                .loginId("spartaAdmin")
                .loginPw("spartaAdmin")
                .email("spartaAdmin@sparta.com")
                .role(UserRole.ROLE_ADMIN)
                .age(20)
                .build();
    }

    public static User createUser() {
        return User.builder()
                .name("르탄이")
                .loginId("sparta")
                .loginPw("sparta")
                .email("sparta@sparta.com")
                .role(UserRole.ROLE_USER)
                .age(20)
                .build();
    }

    public static User createUser2() {
        return User.builder()
                .name("탄탄이")
                .loginId("tantan")
                .loginPw("tantan")
                .email("tantan@sparta.com")
                .role(UserRole.ROLE_USER)
                .age(21)
                .build();
    }

    public static User createUser(String loginId, String loginPw) {
        return User.builder()
                .name("탄탄이")
                .loginId(loginId)
                .loginPw(loginPw)
                .email("tantan@sparta.com")
                .role(UserRole.ROLE_USER)
                .age(21)
                .build();
    }

    public static SignupRequest createSignupReq() {
        return SignupRequest.builder()
                .email("temp@naver.com")
                .adminKey("temp")
                .wantAdmin(false)
                .loginId("temp")
                .loginPw("temp")
                .loginPwAgain("temp")
                .build();
    }

}
