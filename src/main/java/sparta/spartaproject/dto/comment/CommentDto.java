package sparta.spartaproject.dto.comment;

import lombok.Builder;
import lombok.Getter;
import sparta.spartaproject.entity.comment.Comment;
import sparta.spartaproject.entity.post.Post;
import sparta.spartaproject.entity.user.User;

import java.time.LocalDateTime;

public class CommentDto {

    @Getter
    public static class CommentReq {
        String content;

        public static Comment toEntity(CommentReq req, Post post, User user) {
            return Comment.builder()
                    .user(user)
                    .post(post)
                    .content(req.getContent())
                    .build();
        }

    }

    @Builder
    @Getter
    public static class CommentRes {

        private String content;
        private String userId;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public static CommentRes of(Comment comment) {
            return CommentRes.builder()
                    .content(comment.getContent())
                    .userId(comment.getUser().getLoginId())
                    .createdAt(comment.getCreatedAt())
                    .modifiedAt(comment.getModifiedAt())
                    .build();
        }
    }

}
