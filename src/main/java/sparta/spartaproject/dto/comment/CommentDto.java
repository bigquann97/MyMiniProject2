package sparta.spartaproject.dto.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import sparta.spartaproject.entity.comment.Comment;
import sparta.spartaproject.entity.post.Post;
import sparta.spartaproject.entity.user.User;

import java.time.LocalDateTime;

public final class CommentDto {

    @Getter
    @RequiredArgsConstructor
    public static final class CommentRequest {
        @ApiModelProperty(value = "댓글", notes = "댓글 내용을 입력해주세요", required = true, example = "좋아요 누르고 갑니다")
        private final String content;

        public static Comment toEntity(CommentRequest req, Post post, User user) {
            return Comment.builder()
                    .user(user)
                    .post(post)
                    .content(req.getContent())
                    .build();
        }
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static final class CommentResponse {
        private final String content;
        private final String userId;
        private final LocalDateTime createdAt;
        private final LocalDateTime modifiedAt;

        public static CommentResponse of(Comment comment) {
            return CommentResponse.builder()
                    .content(comment.getContent())
                    .userId(comment.getUser().getLoginId())
                    .createdAt(comment.getCreatedAt())
                    .modifiedAt(comment.getModifiedAt())
                    .build();
        }
    }

}
