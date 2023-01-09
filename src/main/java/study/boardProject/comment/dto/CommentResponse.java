package study.boardProject.comment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import study.boardProject.comment.entity.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class CommentResponse {

    private final Long id;
    private final String content;
    private final String nickname;
    private final Long likeCount;
    private final LocalDateTime createdAt;
    private final List<CommentResponse> replies = new ArrayList<>();

    @Builder
    public CommentResponse(Long id, String content, String nickname, Long likeCount, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.nickname = nickname;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
    }

    public static CommentResponse of(Comment comment, Long likeCount) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .nickname(comment.getUser().getNickname())
                .createdAt(comment.getCreatedAt())
                .likeCount(likeCount)
                .build();
    }

    public void addReply(CommentResponse commentResponse) {
        this.replies.add(commentResponse);
    }
}
