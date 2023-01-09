package study.boardProject.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
public final class LoginRequest {

    @ApiModelProperty(value = "아이디", notes = "아이디를 입력해주세요", required = true, example = "sparta")
    @NotBlank
    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]*$", message = "a-z, 0~9 값만 입력해주세요.")
    private final String loginId;

    @ApiModelProperty(value = "비밀번호", notes = "비밀번호를 입력해주세요", required = true, example = "sparta")
    @NotBlank
    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "a-z, A-Z, 0~9 값만 입력해주세요.")
    private final String loginPw;

}
