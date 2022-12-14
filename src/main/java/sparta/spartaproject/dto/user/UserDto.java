package sparta.spartaproject.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import sparta.spartaproject.entity.user.User;
import sparta.spartaproject.entity.user.UserRole;
import sparta.spartaproject.exception.AdminKeyNotMatchException;

import javax.validation.constraints.*;

public class UserDto {

    @Builder
    @Getter
    public static class SignupRes {

        private String loginId;
        private String name;
        private String email;
        private int age;

        public static SignupRes of(User user) {
            return SignupRes.builder()
                    .loginId(user.getLoginId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .age(user.getAge())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class SignUpReq {

        @ApiModelProperty(value = "아이디", notes = "아이디를 입력해주세요", required = true, example = "sparta")
        @NotBlank
        @Size(min = 4, max = 10)
        @Pattern(regexp = "^[a-z0-9]*$", message = "a-z, 0~9 값만 입력해주세요.")
        private String loginId;

        @ApiModelProperty(value = "비밀번호", required = true, example = "sparta")
        @NotBlank
        @Size(min = 4, max = 10)
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "a-z, A-Z, 0~9 값만 입력해주세요.")
        private String loginPw;

        @ApiModelProperty(value = "비밀번호 재입력", required = true, example = "sparta")
        private String loginPwAgain;

        @ApiModelProperty(value = "사용자 이름", notes = "사용자 이름은 한글로 입력해주세요.", required = true, example = "김관호")
        @NotBlank
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣]*$", message = "한글만 입력해주세요.")
        private String name;

        @ApiModelProperty(value = "이메일", notes = "이메일 형식에 맞춰서 입력해주세요", required = true, example = "sparta@naver.com")
        @NotBlank
        @Email
        private String email;

        @ApiModelProperty(value = "나이", notes = "나이는 1 ~ 130 의 숫자만 가능합니다.", required = true, example = "26")
        @Positive
        @Max(value = 130, message = "1 ~ 130 사이 숫자를 입력해주세요.")
        private Integer age;

        @ApiModelProperty(value = "어드민 가입 여부", notes = "어드민 가입 여부", required = true, example = "false")
        private boolean wantAdmin;

        @ApiModelProperty(value = "어드민 가입 키", notes = "어드민 가입 키", required = true, example = "makeMeAdmin")
        private String adminKey;

        public boolean validatePw() {
            return this.loginPw.equals(loginPwAgain);
        }

        public void changePw(String encodedPw) {
            this.loginPw = encodedPw;
        }

        public boolean checkAdminKey() {
            return this.adminKey.equals("makeMeAdmin");
        }

        public static User toEntity(SignUpReq signUpReq) {
            if (signUpReq.isWantAdmin() && signUpReq.checkAdminKey()) {
                return User.builder()
                        .loginId(signUpReq.getLoginId())
                        .loginPw(signUpReq.getLoginPw())
                        .age(signUpReq.getAge())
                        .email(signUpReq.getEmail())
                        .name(signUpReq.getName())
                        .role(UserRole.ROLE_ADMIN)
                        .build();
            } else if (signUpReq.isWantAdmin() && !signUpReq.checkAdminKey()) {
                throw new AdminKeyNotMatchException();
            }
            return User.builder()
                    .loginId(signUpReq.getLoginId())
                    .loginPw(signUpReq.getLoginPw())
                    .age(signUpReq.getAge())
                    .email(signUpReq.getEmail())
                    .name(signUpReq.getName())
                    .role(UserRole.ROLE_USER)
                    .build();
        }
    }

    @Getter
    public static class LoginReq {

        @ApiModelProperty(value = "아이디", notes = "아이디를 입력해주세요", required = true, example = "sparta")
        @NotBlank
        @Size(min = 4, max = 10)
        @Pattern(regexp = "^[a-z0-9]*$", message = "a-z, 0~9 값만 입력해주세요.")
        private String loginId;


        @ApiModelProperty(value = "비밀번호", notes = "비밀번호를 입력해주세요", required = true, example = "sparta")
        @NotBlank
        @Size(min = 4, max = 10)
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "a-z, A-Z, 0~9 값만 입력해주세요.")
        private String loginPw;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(loginId, loginPw);
        }

    }
}
