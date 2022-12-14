package sparta.spartaproject.entity.comment;

import lombok.*;
import sparta.spartaproject.dto.comment.CommentDto;
import sparta.spartaproject.entity.common.TimeStamped;
import sparta.spartaproject.entity.post.Post;
import sparta.spartaproject.entity.user.User;

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

    public void editComment(CommentDto.CommentReq commentReq) {
        this.content = commentReq.getContent();
    }
}
