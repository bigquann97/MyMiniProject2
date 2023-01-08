package study.boardProject.comment.dto;

import lombok.Builder;
import lombok.Getter;
import study.boardProject.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
public final class CommentResponse {

    private final String content;
    private final String nickname;
    private final Long likeCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    @Builder
    public CommentResponse(String content, String nickname, Long likeCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.content = content;
        this.nickname = nickname;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .content(comment.getContent())
                .nickname(comment.getUser().getNickname())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .likeCount(0L)
                .build();
    }

}
