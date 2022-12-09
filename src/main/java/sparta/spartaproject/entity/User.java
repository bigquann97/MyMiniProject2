package sparta.spartaproject.entity;

import lombok.*;
import sparta.spartaproject.dto.SignupRequestDto;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String loginPw;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public static User of(SignupRequestDto signupRequestDto) {
        return User.builder()
                .loginId(signupRequestDto.getLoginId())
                .loginPw(signupRequestDto.getLoginPw())
                .age(signupRequestDto.getAge())
                .email(signupRequestDto.getEmail())
                .name(signupRequestDto.getName())
                .role(UserRole.USER)
                .build();
    }


    public boolean hasThisPost(Post findPost) {
        return true;
    }
}
