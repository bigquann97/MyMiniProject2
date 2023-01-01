package study.boardProject.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.boardProject.dto.comment.CommentResponse;
import study.boardProject.dto.post.PostSimpleResponse;
import study.boardProject.entity.*;
import study.boardProject.repository.CommentRepository;
import study.boardProject.repository.LikeRepository;
import study.boardProject.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

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
        return posts.stream().map(PostSimpleResponse::of).toList();
    }

    @Override
    public List<CommentResponse> getMyComments(User user) {
        List<Comment> comments = commentRepository.findCommentsByUserLoginIdOrderByCreatedAtDesc(user.getLoginId());
        return comments.stream().map(CommentResponse::of).toList();
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
        return posts.stream().map(PostSimpleResponse::of).toList();
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
        return comments.stream().map(CommentResponse::of).toList();
    }
}
