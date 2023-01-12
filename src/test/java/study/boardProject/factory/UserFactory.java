package study.boardProject.factory;

import study.boardProject.auth.entity.User;
import study.boardProject.auth.entity.UserRole;

public class UserFactory {

    public static User buildUser1() {
        return User.builder()
                .loginId("id")
                .loginPw("pw")
                .email("email")
                .name("name")
                .age(30)
                .nickname("nickname")
                .role(UserRole.USER)
                .build();
    }

    public static User buildUser2() {
        return User.builder()
                .loginId("id2")
                .loginPw("pw2")
                .email("email2")
                .name("name2")
                .age(302)
                .nickname("nickname2")
                .role(UserRole.USER)
                .build();
    }

    public static User buildAdmin() {
        return User.builder()
                .loginId("id")
                .loginPw("pw")
                .email("email")
                .name("name")
                .age(30)
                .nickname("nickname")
                .role(UserRole.ADMIN)
                .build();
    }

}
