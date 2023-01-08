package study.boardProject.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import study.boardProject.auth.entity.User;
import study.boardProject.auth.entity.UserRole;

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

    @ApiModelProperty(value = "닉네임", notes = "닉네임", required = true, example = "닉네임")
    @NotBlank
    private final String nickname;

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

    public User toEntity(String encodedPw) {
        return User.builder()
                .loginId(this.getLoginId())
                .loginPw(encodedPw)
                .age(this.getAge())
                .email(this.getEmail())
                .nickname(this.getNickname())
                .name(this.getName())
                .role(UserRole.USER)
                .build();
    }

}
