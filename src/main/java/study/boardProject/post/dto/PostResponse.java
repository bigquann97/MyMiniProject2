package study.boardProject.post.dto;

import lombok.Builder;
import lombok.Getter;
import study.boardProject.comment.dto.CommentResponse;
import study.boardProject.comment.entity.Comment;
import study.boardProject.post.entity.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public final class PostResponse {

    private final String nickname;
    private final String title;
    private final String content;
    private final List<CommentResponse> comments;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final Long likeCount;

    @Builder
    public PostResponse(String nickname, String title, String content, List<CommentResponse> comments, LocalDateTime createdAt, LocalDateTime modifiedAt, Long likeCount) {
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.comments = comments;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.likeCount = likeCount;
    }

    public static PostResponse of(Post post, List<Comment> comments, long likeCount) {
        List<CommentResponse> commentResponses = comments.stream().map(CommentResponse::of).collect(Collectors.toList());
        return PostResponse.builder()
                .nickname(post.getUser().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .comments(commentResponses)
                .likeCount(0L)
                .build();
    }

}
