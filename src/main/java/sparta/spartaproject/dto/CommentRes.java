package sparta.spartaproject.dto;

import lombok.Builder;
import lombok.Getter;
import sparta.spartaproject.entity.Comment;

import java.time.LocalDateTime;

@Builder
@Getter
public class CommentRes {

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
