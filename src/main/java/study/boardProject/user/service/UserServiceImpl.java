package study.boardProject.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.boardProject.auth.entity.User;
import study.boardProject.comment.entity.Comment;
import study.boardProject.comment.dto.CommentResponse;
import study.boardProject.post.entity.Post;
import study.boardProject.post.dto.PostSimpleResponse;
import study.boardProject.comment.repository.CommentRepository;
import study.boardProject.like.entity.Like;
import study.boardProject.like.entity.LikeCategory;
import study.boardProject.like.repository.LikeRepository;
import study.boardProject.post.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Override
    public List<PostSimpleResponse> getMyPosts(User user) {
        List<Post> posts = postRepository.findPostsByUserLoginIdOrderByCreatedAtDesc(user.getLoginId());
        return posts.stream().map(PostSimpleResponse::of).collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> getMyComments(User user) {
        List<Comment> comments = commentRepository.findCommentsByUserLoginIdOrderByCreatedAtDesc(user.getLoginId());
        return comments.stream().map(CommentResponse::of).collect(Collectors.toList());
    }

    @Override
    public List<PostSimpleResponse> getMyLikePosts(User user) {
        List<Like> likes = likeRepository.findLikesByUserIdAndCategoryOrderByCreatedAt(user.getId(), LikeCategory.POST);
        List<Post> posts = new ArrayList<>();
        for (Like like : likes) {
            Long postId = like.getTargetId();
            Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
            posts.add(post);
        }
        return posts.stream().map(PostSimpleResponse::of).collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> getMyLikeComments(User user) {
        List<Like> likes = likeRepository.findLikesByUserIdAndCategoryOrderByCreatedAt(user.getId(), LikeCategory.COMMENT);
        List<Comment> comments = new ArrayList<>();
        for (Like like : likes) {
            Long commentId = like.getTargetId();
            Comment comment = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);
            comments.add(comment);
        }
        return comments.stream().map(CommentResponse::of).collect(Collectors.toList());
    }
}
