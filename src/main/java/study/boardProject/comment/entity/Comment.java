package study.boardProject.comment.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.boardProject.auth.entity.User;
import study.boardProject.common.entity.TimeStamp;
import study.boardProject.post.entity.Post;

import javax.persistence.*;

import static study.boardProject.common.exception.AuthException.AuthenticationException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private Comment parent;

    public void editComment(String content) {
        this.content = content;
    }

    @Builder
    public Comment(String content, User user, Post post, Comment parent) {
        this.content = content;
        this.user = user;
        this.post = post;
        this.parent = parent;
    }

    public void validateUser(User attemptUser) {
        if (!this.user.equals(attemptUser)) {
            throw new AuthenticationException();
        }
    }

    public boolean isReply() {
        return this.parent != null;
    }
}
