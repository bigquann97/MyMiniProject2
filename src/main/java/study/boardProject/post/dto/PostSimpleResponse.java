package study.boardProject.post.dto;

import lombok.Builder;
import lombok.Getter;
import study.boardProject.post.entity.Post;

import java.time.LocalDateTime;

@Getter
public final class PostSimpleResponse {

    private final Long id;
    private final String nickname;
    private final String title;
    private final Long likeCount;
    private final LocalDateTime createdAt;

    @Builder
    public PostSimpleResponse(Long id, String nickname, String title, Long likeCount, LocalDateTime createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
    }

    public static PostSimpleResponse of(Post post) {
        return PostSimpleResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .nickname(post.getUser().getNickname())
                .likeCount(0L)
                .createdAt(post.getCreatedAt())
                .build();
    }

}
