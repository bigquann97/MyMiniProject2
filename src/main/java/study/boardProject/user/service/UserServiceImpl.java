package study.boardProject.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.boardProject.auth.entity.User;
import study.boardProject.comment.dto.CommentResponse;
import study.boardProject.comment.entity.Comment;
import study.boardProject.comment.repository.CommentRepository;
import study.boardProject.like.entity.Like;
import study.boardProject.like.repository.LikeRepository;
import study.boardProject.post.dto.PostSimpleResponse;
import study.boardProject.post.entity.Post;
import study.boardProject.post.repository.PostRepository;

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
    public List<PostSimpleResponse> getMyPosts(int page, Pageable pageable, User user) {
        List<PostSimpleResponse> result = new ArrayList<>();
        Page<Post> pagedPosts = postRepository.findByUser(user, pageable.withPage(page));
        for (Post post : pagedPosts) {
            long likeCount = likeRepository.countByPost(post);
            PostSimpleResponse element = PostSimpleResponse.of(post, likeCount);
            result.add(element);
        }
        return result;
    }

    @Override
    public List<CommentResponse> getMyComments(int page, Pageable pageable, User user) {
        List<CommentResponse> result = new ArrayList<>();
        Page<Comment> pagedComment = commentRepository.findByUser(user, pageable.withPage(page));
        for (Comment comment : pagedComment) {
            long likeCount = likeRepository.countByComment(comment);
            CommentResponse element = CommentResponse.of(comment, likeCount);
            result.add(element);
        }
        return result;
    }

    @Override
    public List<PostSimpleResponse> getMyLikePosts(int page, Pageable pageable, User user) {
        List<PostSimpleResponse> result = new ArrayList<>();
        Page<Like> pagedPostLikes = likeRepository.findByUserAndCommentNull(user, pageable.withPage(page));
        for (Like like : pagedPostLikes) {
            Post post = like.getPost();
            long likeCount = likeRepository.countByPost(post);
            PostSimpleResponse element = PostSimpleResponse.of(post, likeCount);
            result.add(element);
        }
        return result;
    }

    @Override
    public List<CommentResponse> getMyLikeComments(int page, Pageable pageable, User user) {
        List<CommentResponse> result = new ArrayList<>();
        Page<Like> pagedCommentLikes = likeRepository.findByUserAndPostNull(user, pageable.withPage(page));
        for (Like like : pagedCommentLikes) {
            Comment comment = like.getComment();
            long likeCount = likeRepository.countByComment(comment);
            CommentResponse element = CommentResponse.of(comment, likeCount);
            result.add(element);
        }
        return result;
    }

}
