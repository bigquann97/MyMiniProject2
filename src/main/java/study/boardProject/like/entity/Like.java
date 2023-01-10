package study.boardProject.like.entity;

import lombok.*;
import study.boardProject.auth.entity.User;
import study.boardProject.comment.entity.Comment;
import study.boardProject.common.entity.TimeStamp;
import study.boardProject.post.entity.Post;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "likes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "post_id", "comment_id"})
        }
)
public class Like extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Builder
    public Like(User user, Post post, Comment comment) {
        this.user = user;
        this.post = post;
        this.comment = comment;
    }

}
