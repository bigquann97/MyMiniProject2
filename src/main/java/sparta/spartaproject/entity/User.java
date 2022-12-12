package sparta.spartaproject.entity;

import lombok.*;
import sparta.spartaproject.dto.SignUpReq;
import sparta.spartaproject.exception.AdminKeyNotMatchException;

import javax.persistence.*;
import java.util.ArrayList;
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

    private Integer age;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    public static User of(SignUpReq signUpReq) {
        if (signUpReq.isWantAdmin() && signUpReq.checkAdminKey()) {
            return User.builder()
                    .loginId(signUpReq.getLoginId())
                    .loginPw(signUpReq.getLoginPw())
                    .age(signUpReq.getAge())
                    .email(signUpReq.getEmail())
                    .name(signUpReq.getName())
                    .role(UserRole.ADMIN)
                    .build();
        } else if (signUpReq.isWantAdmin() && !signUpReq.checkAdminKey()) {
            throw new AdminKeyNotMatchException();
        }
        return User.builder()
                .loginId(signUpReq.getLoginId())
                .loginPw(signUpReq.getLoginPw())
                .age(signUpReq.getAge())
                .email(signUpReq.getEmail())
                .name(signUpReq.getName())
                .role(UserRole.USER)
                .build();
    }

    public boolean hasPost(Post findPost) {
        return posts.stream().anyMatch(x -> x.equals(findPost));
    }

    public boolean hasComment(Comment targetComment) {
        return comments.stream().anyMatch(x -> x.equals(targetComment));
    }

    public boolean isAdmin() {
        return this.role.equals(UserRole.ADMIN);
    }
}
