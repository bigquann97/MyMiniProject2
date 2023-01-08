package study.boardProject.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import study.boardProject.comment.dto.CommentResponse;
import study.boardProject.comment.entity.Comment;
import study.boardProject.post.entity.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@RequiredArgsConstructor
public final class PostResponse { // PostResponseDto = Post + comment List
     // PostResponseDto = Post(내부에 commentList)

    private final String author;
    private final String title;
    private final String content;
    private final List<CommentResponse> comments;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final Long likeCount;

    public static PostResponse of(Post post, List<Comment> comments) {
        List<CommentResponse> commentResponses = comments.stream().map(CommentResponse::of).collect(Collectors.toList());
        return PostResponse.builder()
                .author(post.getUserLoginId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .comments(commentResponses)
                .likeCount(post.getLikeCount().get())
                .build();
    }
}
