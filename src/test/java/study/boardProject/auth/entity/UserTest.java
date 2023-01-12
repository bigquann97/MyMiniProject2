package study.boardProject.auth.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.boardProject.factory.UserFactory;

import static org.assertj.core.api.Assertions.*;

public class UserTest {

    @DisplayName("1. 유저 빌더 테스트")
    @Test
    void test_1() {
        //given
        String id = "id";
        String pw = "pw";
        String email = "email";
        String name = "name";
        int age = 30;
        UserRole role = UserRole.USER;
        String nickname = "nickname";

        //when
        User user = User.builder()
                .loginId(id)
                .loginPw(pw)
                .email(email)
                .name(name)
                .age(age)
                .role(role)
                .nickname(nickname)
                .build();

        //then
        assertThat(user.getId()).isNull();
        assertThat(user.getLoginId()).isEqualTo(id);
        assertThat(user.getLoginPw()).isEqualTo(pw);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getAge()).isEqualTo(age);
        assertThat(user.getRole()).isEqualTo(role);
        assertThat(user.getNickname()).isEqualTo(nickname);

    }

    @DisplayName("2. 유저 동일성 테스트")
    @Test
    void test_2() {
        //given
        User user1 = UserFactory.buildUser1();
        User user1Same = UserFactory.buildUser1();
        User user2 = UserFactory.buildUser2();

        //when
        boolean compareUser1AndUser1Same = user1.equals(user1Same);
        boolean compareUser1AndUser2 = user1.equals(user2);

        //then
        assertThat(compareUser1AndUser1Same).isTrue();
        assertThat(compareUser1AndUser2).isFalse();
    }
}
