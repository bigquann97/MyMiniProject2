package study.boardProject.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import study.boardProject.entity.Post;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public final class PostSimpleResponse {

    private final Long id;
    private final String title;
    private final Long likeCount;
    private final LocalDateTime createdAt;

    public static PostSimpleResponse of(Post post) {
        return PostSimpleResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .likeCount(post.getLikeCount().get())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
