package sparta.spartaproject.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.*;

@Builder
@Getter
public class SignUpReq {
    @NotBlank
    @Size(min = 4, max = 10)
//    @Pattern(regexp = "/[a-z0-9]/")
    private String loginId;

    @NotBlank
    @Size(min = 4, max = 10)
//    @Pattern(regexp = "/[a-zA-Z0-9]/")
    private String loginPw;

    private String loginPwAgain;

    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣]*$", message = "한글만 입력해주세요")
    private String name;

    @NotBlank
    @Email
    private String email;

    @Positive
    @Max(value = 130)
    private Integer age;

    public boolean validatePw() {
        return this.loginPw.equals(loginPwAgain);
    }

    public void changePw(String encodedPw) {
        this.loginPw = encodedPw;
    }
}

/*
{
    "userId": "sparta123",
    "userPw": "sparta123",
    "userPwAgain": "sparta123",
    "name": "김관호",
    "email": "sparta@naver.com",
    "age" : 26
}
 */
