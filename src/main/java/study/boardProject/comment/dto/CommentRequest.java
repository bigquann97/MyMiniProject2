package study.boardProject.comment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import study.boardProject.auth.entity.User;
import study.boardProject.comment.entity.Comment;
import study.boardProject.post.entity.Post;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public final class CommentRequest {

    @ApiModelProperty(value = "댓글", notes = "댓글 내용을 입력해주세요", required = true)
    private final String content;

    public Comment toEntity(Post post, User user) {
        return Comment.builder()
                .user(user)
                .post(post)
                .content(this.getContent())
                .build();
    }

    public Comment toEntity(Post post, User user, Comment parent) {
        return Comment.builder()
                .user(user)
                .post(post)
                .parent(parent)
                .content(this.getContent())
                .build();
    }

}
