package sparta.spartaproject.dto;

import com.sun.istack.Nullable;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.*;

@Builder
@Getter
public class SignUpReq {
    @NotBlank
    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]*$", message = "a-z, 0~9 값만 입력해주세요.")
    private String loginId;

    @NotBlank
    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "a-z, A-Z, 0~9 값만 입력해주세요.")
    private String loginPw;

    private String loginPwAgain;

    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣]*$", message = "한글만 입력해주세요.")
    private String name;

    @NotBlank
    @Email
    private String email;

    @Positive
    @Max(value = 130, message = "1 ~ 130 사이 숫자를 입력해주세요.")
    private Integer age;

    private boolean wantAdmin;

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
}