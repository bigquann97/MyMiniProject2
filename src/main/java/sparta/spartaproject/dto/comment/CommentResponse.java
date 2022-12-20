package sparta.spartaproject.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sparta.spartaproject.entity.Comment;

import java.time.LocalDateTime;

@Builder
@Getter
@RequiredArgsConstructor
public final class CommentResponse {

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
