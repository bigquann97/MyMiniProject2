package sparta.spartaproject.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sparta.spartaproject.entity.User;

@Getter
@Builder
@RequiredArgsConstructor
public final class SignupResponse {

    private final String loginId;
    private final String name;
    private final String email;
    private final int age;

    public static SignupResponse of(User user) {
        return SignupResponse.builder()
                .loginId(user.getLoginId())
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .build();
    }

}
