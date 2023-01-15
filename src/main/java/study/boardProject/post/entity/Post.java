package study.boardProject.post.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import study.boardProject.auth.entity.User;
import study.boardProject.common.entity.TimeStamp;

import javax.persistence.*;

import static study.boardProject.common.exception.AuthException.AuthenticationException;

@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void editPost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validateUser(User attemptUser) {
        if (!this.user.equals(attemptUser)) {
            throw new AuthenticationException();
        }
    }

}
