package sparta.spartaproject.entity;

import lombok.*;
import sparta.spartaproject.dto.CommentReq;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id")
    private Post post;

    public static Comment of(CommentReq commentReq, Post post, User user) {
        return Comment.builder()
                .content(commentReq.getContent())
                .user(user)
                .post(post)
                .build();
    }

    public void editComment(CommentReq commentReq) {
        this.content = commentReq.getContent();
    }
}
