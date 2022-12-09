package sparta.spartaproject.dto;

import lombok.Builder;
import lombok.Getter;
import sparta.spartaproject.entity.User;

@Builder
@Getter
public class SignupResponseDto {

    private String loginId;
    private String name;
    private String email;
    private int age;

    public static SignupResponseDto of(User user) {
        return SignupResponseDto.builder()
                .loginId(user.getLoginId())
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .build();
    }
}
