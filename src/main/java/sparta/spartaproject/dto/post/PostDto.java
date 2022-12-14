package sparta.spartaproject.dto.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import sparta.spartaproject.dto.comment.CommentDto;
import sparta.spartaproject.entity.post.Post;
import sparta.spartaproject.entity.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class PostDto {

    @Getter
    public static class PostReq {
        @ApiModelProperty(value = "제목", notes = "제목을 입력해주세요", required = true, example = "제목")
        private String title;

        @ApiModelProperty(value = "내용", notes = "내용을 입력해주세요", required = true, example = "내용")
        private String content;

        public static Post toEntity(PostDto.PostReq req, User user) {
            return Post.builder()
                    .title(req.getTitle())
                    .content(req.getContent())
                    .user(user)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class PostRes {

        private String author;
        private String title;
        private String content;
        private List<CommentDto.CommentRes> comments;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public static PostRes of(Post post) {
            List<CommentDto.CommentRes> comments = post.getComments().stream().map(CommentDto.CommentRes::of).toList();
            return PostRes.builder()
                    .author(post.getUser().getLoginId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .comments(comments)
                    .build();
        }

    }

    @Getter
    @Builder
    public static class PostSimpleRes {
        private Long id;
        private String title;
        public static PostSimpleRes of(Post post) {
            return PostSimpleRes.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .build();
        }
    }
}
