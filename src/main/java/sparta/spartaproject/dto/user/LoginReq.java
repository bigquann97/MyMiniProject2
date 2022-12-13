package sparta.spartaproject.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class LoginReq {

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
