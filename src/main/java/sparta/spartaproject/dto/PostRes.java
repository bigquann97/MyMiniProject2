package sparta.spartaproject.dto;

import lombok.Builder;
import lombok.Getter;
import sparta.spartaproject.entity.Post;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostRes {

    private String author;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAy;

    public static PostRes of(Post post) {
        return PostRes.builder()
                .author(post.getUser().getLoginId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAy(post.getModifiedAt())
                .build();
    }
}
