package study.boardProject.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import study.boardProject.comment.entity.Comment;

import java.time.LocalDateTime;

@Builder
@Getter
@RequiredArgsConstructor
public final class CommentResponse {

    private final String content;
    private final String userLoginId;
    private final Long likeCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .content(comment.getContent())
                .userLoginId(comment.getUserLoginId())
                .likeCount(comment.getLikeCount().get())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
