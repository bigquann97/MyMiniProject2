package study.boardProject.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.boardProject.auth.domain.User;
import study.boardProject.comment.Comment;
import study.boardProject.comment.repository.CommentRepository;
import study.boardProject.like.repository.LikeRepository;
import study.boardProject.like.domain.Like;
import study.boardProject.like.domain.LikeCategory;
import study.boardProject.post.domain.Post;
import study.boardProject.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public void addLikeOnPost(Long postId, User user) {
        validateIfUserAlreadyLikedPost(postId, user);
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        Like like = Like.builder()
                .targetId(postId)
                .category(LikeCategory.POST)
                .userId(user.getId())
                .build();
        post.addLikeCount();
        likeRepository.save(like);
        postRepository.save(post);
    }

    private void validateIfUserAlreadyLikedPost(Long postId, User user) {
        if(likeRepository.existsByUserIdAndTargetIdAndCategory(user.getId(), postId, LikeCategory.POST))
            throw new IllegalArgumentException();
    }

    @Override
    @Transactional
    public void cancelLikeOnPost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        long deletedCount = likeRepository.deleteByUserIdAndTargetIdAndCategory(user.getId(), postId, LikeCategory.POST);
        if(deletedCount == 0) throw new IllegalArgumentException();
        post.minusLikeCount();
        postRepository.save(post);
    }

    @Override
    @Transactional
    public void addLikeOnComment(Long commentId, User user) {
        if(!likeRepository.existsByUserIdAndTargetIdAndCategory(user.getId(), commentId, LikeCategory.COMMENT)) {
            Comment comment = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);
            Like like = Like.builder()
                    .targetId(commentId)
                    .category(LikeCategory.COMMENT)
                    .userId(user.getId())
                    .build();
            comment.addLikeCount();
            likeRepository.save(like);
            commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Transactional
    public void cancelLikeOnComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);
        long deletedCount = likeRepository.deleteByUserIdAndTargetIdAndCategory(user.getId(), commentId, LikeCategory.COMMENT);
        if(deletedCount == 0) throw new IllegalArgumentException();
        comment.minusLikeCount();
        commentRepository.save(comment);
    }

}
