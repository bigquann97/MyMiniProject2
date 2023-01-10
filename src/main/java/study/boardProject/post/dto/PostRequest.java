package study.boardProject.post.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import study.boardProject.auth.entity.User;
import study.boardProject.post.entity.Post;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public final class PostRequest {

    @ApiModelProperty(value = "제목", notes = "제목을 입력해주세요", required = true, example = "제목")
    private final String title;

    @ApiModelProperty(value = "내용", notes = "내용을 입력해주세요", required = true, example = "내용")
    private final String content;

    public Post toEntity(User user) {
        return Post.builder()
                .title(this.getTitle())
                .content(this.getContent())
                .user(user)
                .build();
    }

}
