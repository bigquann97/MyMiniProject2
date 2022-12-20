package sparta.spartaproject.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    public boolean hasPost(Post findPost) {
        return posts.stream().anyMatch(x -> x.equals(findPost));
    }

    public boolean hasComment(Comment targetComment) {
        return comments.stream().anyMatch(x -> x.equals(targetComment));
    }

    public boolean isAdmin() {
        return this.role.equals(UserRole.ROLE_ADMIN);
    }

    @Builder
    public User(Long id, String loginId, String loginPw, String name, String email, Integer age, UserRole role) {
        this.id = id;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
        this.email = email;
        this.age = age;
        this.role = role;
    }
}
