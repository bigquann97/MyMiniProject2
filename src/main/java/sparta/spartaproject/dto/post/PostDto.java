package sparta.spartaproject.dto.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sparta.spartaproject.dto.comment.CommentDto;
import sparta.spartaproject.entity.post.Post;
import sparta.spartaproject.entity.user.User;

import java.time.LocalDateTime;
import java.util.List;

public final class PostDto {

    @Getter
    @RequiredArgsConstructor
    public static final class PostRequest {
        @ApiModelProperty(value = "제목", notes = "제목을 입력해주세요", required = true, example = "제목")
        private final String title;

        @ApiModelProperty(value = "내용", notes = "내용을 입력해주세요", required = true, example = "내용")
        private final String content;

        public static Post toEntity(PostRequest req, User user) {
            return Post.builder()
                    .title(req.getTitle())
                    .content(req.getContent())
                    .user(user)
                    .build();
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static final class PostResponse {

        private final String author;
        private final String title;
        private final String content;
        private final List<CommentDto.CommentResponse> comments;
        private final LocalDateTime createdAt;
        private final LocalDateTime modifiedAt;

        public static PostResponse of(Post post) {
            List<CommentDto.CommentResponse> comments = post.getComments().stream().map(CommentDto.CommentResponse::of).toList();
            return PostResponse.builder()
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
    @RequiredArgsConstructor
    public static final class PostSimpleResponse {
        private final Long id;
        private final String title;
        public static PostSimpleResponse of(Post post) {
            return PostSimpleResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .build();
        }
    }
}
