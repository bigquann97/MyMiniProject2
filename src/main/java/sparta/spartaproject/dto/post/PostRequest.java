package sparta.spartaproject.dto.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sparta.spartaproject.entity.Post;
import sparta.spartaproject.entity.User;

@Getter
@RequiredArgsConstructor
public final class PostRequest {

    @ApiModelProperty(value = "제목", notes = "제목을 입력해주세요", required = true, example = "제목")
    private final String title;

    @ApiModelProperty(value = "내용", notes = "내용을 입력해주세요", required = true, example = "내용")
    private final String content;

    public Post toEntity(User user) {
        return Post.builder()
                .title(this.getTitle())
                .content(this.getContent())
                .userLoginId(user.getLoginId())
                .build();
    }
}
