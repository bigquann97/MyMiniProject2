package sparta.spartaproject.entity;

import lombok.*;
import sparta.spartaproject.dto.SignUpReq;

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
        return User.builder()
                .loginId(signUpReq.getLoginId())
                .loginPw(signUpReq.getLoginPw())
                .age(signUpReq.getAge())
                .email(signUpReq.getEmail())
                .name(signUpReq.getName())
                .role(UserRole.USER)
                .build();
    }

    public boolean hasThisPost(Post findPost) {
        for (Post post : posts) {
            if (post.equals(findPost)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasThisComment(Comment targetComment) {
        for (Comment comment : this.comments) {
            if (comment.equals(targetComment)) {
                return true;
            }
        }
        return false;
    }
}
