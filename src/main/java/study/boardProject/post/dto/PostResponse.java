package study.boardProject.post.dto;

import lombok.Builder;
import lombok.Getter;
import study.boardProject.comment.dto.CommentResponse;
import study.boardProject.comment.entity.Comment;
import study.boardProject.post.entity.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static study.boardProject.common.exception.CommentException.*;

@Getter
public final class PostResponse {

    private final Long id;
    private final String nickname;
    private final String title;
    private final String content;
    private final List<CommentResponse> comments;
    private final LocalDateTime createdAt;
    private final Long likeCount;

    @Builder
    public PostResponse(Long id, String nickname, String title, String content, List<CommentResponse> comments, LocalDateTime createdAt, Long likeCount) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.comments = comments;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
    }

    public static PostResponse of(Post post, long postLikeCount, Map<Comment, Long> commentAndLikeCount) {
        List<CommentResponse> comments = new ArrayList<>();

        for (Comment comment : commentAndLikeCount.keySet()) {
            Long likeCount = commentAndLikeCount.get(comment);
            CommentResponse responseDto = CommentResponse.of(comment, likeCount);
            if(comment.isReply()) {
                Comment parent = comment.getParent();
                CommentResponse parentDto = comments.stream()
                        .filter(x -> x.getId().equals(parent.getId()))
                        .findFirst()
                        .orElseThrow(CommentNotFoundException::new);
                parentDto.addReply(responseDto);
                continue;
            }
            comments.add(responseDto);
        }

        return PostResponse.builder()
                .id(post.getId())
                .nickname(post.getUser().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .comments(comments)
                .likeCount(postLikeCount)
                .build();
    }

}
