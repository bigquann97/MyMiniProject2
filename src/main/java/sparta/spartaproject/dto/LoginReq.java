package sparta.spartaproject.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class LoginReq {
    @NotBlank
    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]*$", message = "a-z, 0~9 값만 입력해주세요.")
    private String loginId;

    @NotBlank
    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "a-z, A-Z, 0~9 값만 입력해주세요.")
    private String loginPw;
}
