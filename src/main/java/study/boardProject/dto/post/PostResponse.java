package study.boardProject.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import study.boardProject.dto.comment.CommentResponse;
import study.boardProject.entity.Comment;
import study.boardProject.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

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

    public static PostResponse of(Post post, List<Comment> comments) {
        List<CommentResponse> commentResponses = comments.stream().map(x -> CommentResponse.of(x, x.getUserLoginId())).toList();
        return PostResponse.builder()
                .author(post.getUserLoginId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .comments(commentResponses)
                .build();
    }
}
