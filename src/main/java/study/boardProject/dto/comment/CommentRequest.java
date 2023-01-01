package study.boardProject.dto.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import study.boardProject.entity.Comment;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public final class CommentRequest {

    @ApiModelProperty(value = "댓글", notes = "댓글 내용을 입력해주세요", required = true)
    private final String content;

    public Comment toEntity(Long postId, String userLoginId) {
        return Comment.builder()
                .userLoginId(userLoginId)
                .postId(postId)
                .content(this.getContent())
                .likeCount(new AtomicLong(0L))
                .build();
    }
}
