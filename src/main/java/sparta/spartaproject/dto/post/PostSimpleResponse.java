package sparta.spartaproject.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sparta.spartaproject.entity.Post;

@Getter
@Builder
@RequiredArgsConstructor
public final class PostSimpleResponse {

    private final Long id;
    private final String title;

    public static PostSimpleResponse of(Post post) {
        return PostSimpleResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .build();
    }
}
