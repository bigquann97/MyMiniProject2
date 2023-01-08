package study.boardProject.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.boardProject.auth.entity.User;
import study.boardProject.comment.entity.Comment;
import study.boardProject.comment.repository.CommentRepository;
import study.boardProject.like.entity.Like;
import study.boardProject.like.repository.LikeRepository;
import study.boardProject.post.entity.Post;
import study.boardProject.post.repository.PostRepository;

import static study.boardProject.common.exception.CommentException.CommentNotFoundException;
import static study.boardProject.common.exception.LikeException.AlreadyLikedException;
import static study.boardProject.common.exception.LikeException.LikeNotFoundException;
import static study.boardProject.common.exception.PostException.PostNotFoundException;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public void addLikeOnPost(Long postId, User user) {
        Post post = validateIfUserCanLikePostAndGetPost(postId, user);
        Like like = Like.builder()
                .post(post)
                .user(user)
                .build();
        likeRepository.save(like);
    }

    @Override
    @Transactional
    public void cancelLikeOnPost(Long postId, User user) {
        Post post = validateIfUserCanCancelLikeOnPostAndGetPost(postId, user);
        likeRepository.deleteByPostAndUser(post, user);
    }

    @Override
    @Transactional
    public void addLikeOnComment(Long commentId, User user) {
        Comment comment = validateIfUserCanLikeCommentAndGetComment(commentId, user);
        Like like = Like.builder()
                .comment(comment)
                .user(user)
                .build();
        likeRepository.save(like);
    }

    @Override
    @Transactional
    public void cancelLikeOnComment(Long commentId, User user) {
        Comment comment = validateIfUserCanCancelLikeOnCommentAndGetComment(commentId, user);
        likeRepository.deleteByCommentAndUser(comment, user);
    }

    private Post validateIfUserCanLikePostAndGetPost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        if(likeRepository.existsByPostAndUser(post, user))
            throw new AlreadyLikedException();
        return post;
    }

    private Post validateIfUserCanCancelLikeOnPostAndGetPost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        if(!likeRepository.existsByPostAndUser(post, user))
            throw new LikeNotFoundException();
        return post;
    }
    private Comment validateIfUserCanLikeCommentAndGetComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        if(likeRepository.existsByCommentAndUser(comment, user))
            throw new AlreadyLikedException();
        return comment;
    }

    private Comment validateIfUserCanCancelLikeOnCommentAndGetComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        if(!likeRepository.existsByCommentAndUser(comment, user))
            throw new LikeNotFoundException();
        return comment;
    }

}
