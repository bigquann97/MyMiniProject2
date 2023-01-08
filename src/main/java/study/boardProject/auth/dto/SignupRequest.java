package study.boardProject.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import study.boardProject.auth.domain.User;
import study.boardProject.auth.domain.UserRole;
import study.boardProject.common.exception.MismatchException;

import javax.validation.constraints.*;

@Builder
@Getter
@RequiredArgsConstructor
public final class SignupRequest {

    @ApiModelProperty(value = "아이디", notes = "아이디를 입력해주세요", required = true, example = "sparta")
    @NotBlank
    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]*$", message = "a-z, 0~9 값만 입력해주세요.")
    private final String loginId;

    @ApiModelProperty(value = "비밀번호", required = true, example = "sparta")
    @NotBlank
    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "a-z, A-Z, 0~9 값만 입력해주세요.")
    private final String loginPw;

    @ApiModelProperty(value = "비밀번호 재입력", required = true, example = "sparta")
    private final String loginPwAgain;

    @ApiModelProperty(value = "사용자 이름", notes = "사용자 이름은 한글로 입력해주세요.", required = true, example = "김관호")
    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣]*$", message = "한글만 입력해주세요.")
    private final String name;

    @ApiModelProperty(value = "이메일", notes = "이메일 형식에 맞춰서 입력해주세요", required = true, example = "sparta@naver.com")
    @NotBlank
    @Email
    private final String email;

    @ApiModelProperty(value = "나이", notes = "나이는 1 ~ 130 의 숫자만 가능합니다.", required = true, example = "26")
    @Positive
    @Max(value = 130, message = "1 ~ 130 사이 숫자를 입력해주세요.")
    private final Integer age;

    @ApiModelProperty(value = "어드민 가입 여부", notes = "어드민 가입 여부", required = true, example = "false")
    private final boolean wantAdmin;

    @ApiModelProperty(value = "어드민 가입 키", notes = "어드민 가입 키", required = true, example = "makeMeAdmin")
    private final String adminKey;

    public boolean validatePw() {
        return this.loginPw.equals(loginPwAgain);
    }

    public boolean checkAdminKey() {
        return this.adminKey.equals("makeMeAdmin");
    }

    public User toEntity(String encodedPw) {
        if (this.isWantAdmin() && this.checkAdminKey()) {
            return User.builder()
                    .loginId(this.getLoginId())
                    .loginPw(encodedPw)
                    .age(this.getAge())
                    .email(this.getEmail())
                    .name(this.getName())
                    .role(UserRole.ADMIN)
                    .build();
        } else if (this.isWantAdmin() && !this.checkAdminKey()) {
            throw new MismatchException();
        } else {
            return User.builder()
                    .loginId(this.getLoginId())
                    .loginPw(encodedPw)
                    .age(this.getAge())
                    .email(this.getEmail())
                    .name(this.getName())
                    .role(UserRole.USER)
                    .build();
        }
    }
}
