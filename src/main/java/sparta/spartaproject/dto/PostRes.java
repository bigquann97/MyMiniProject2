package sparta.spartaproject.dto;

import lombok.Builder;
import lombok.Getter;
import sparta.spartaproject.entity.Comment;
import sparta.spartaproject.entity.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PostRes {

    private String author;
    private String title;
    private String content;
    private List<CommentRes> comments;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAy;

    public static PostRes of(Post post) {
        List<CommentRes> comments = post.getComments().stream().map(CommentRes::of).toList();
        return PostRes.builder()
                .author(post.getUser().getLoginId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAy(post.getModifiedAt())
                .comments(comments)
                .build();
    }

}
