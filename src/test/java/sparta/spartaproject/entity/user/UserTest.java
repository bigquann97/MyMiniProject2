package sparta.spartaproject.entity.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sparta.spartaproject.factory.UserFactory;

class UserTest {

    private final ObjectMapper om = new ObjectMapper();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @DisplayName("1. 비밀번호 암호화 테스트")
    @Test
    void test_1() {
        String encodedPw = passwordEncoder.encode("password");
        User user = UserFactory.createUser("loginId", encodedPw);
        Assertions.assertThat(passwordEncoder.matches("wrongPw", user.getLoginPw())).isFalse();
        Assertions.assertThat(passwordEncoder.matches("password", user.getLoginPw())).isTrue();
    }

}