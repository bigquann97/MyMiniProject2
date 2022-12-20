package sparta.spartaproject.dto.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import sparta.spartaproject.entity.Comment;
import sparta.spartaproject.entity.Post;
import sparta.spartaproject.entity.User;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public final class CommentRequest {

    @ApiModelProperty(value = "댓글", notes = "댓글 내용을 입력해주세요", required = true, example = "좋아요 누르고 갑니다")
    private final String content;

    public Comment toEntity(Post post, User user) {
        return Comment.builder()
                .user(user)
                .post(post)
                .content(this.getContent())
                .build();
    }
}
