package study.boardProject.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import study.boardProject.entity.Comment;

import java.time.LocalDateTime;

@Builder
@Getter
@RequiredArgsConstructor
public final class CommentResponse {

    private final String content;
    private final String userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static CommentResponse of(Comment comment, String userLoginId) {
        return CommentResponse.builder()
                .content(comment.getContent())
                .userId(userLoginId)
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
