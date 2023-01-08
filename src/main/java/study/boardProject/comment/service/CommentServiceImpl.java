package study.boardProject.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.boardProject.comment.dto.CommentRequest;
import study.boardProject.comment.entity.Comment;
import study.boardProject.like.entity.LikeCategory;
import study.boardProject.auth.entity.User;
import study.boardProject.comment.repository.CommentRepository;
import study.boardProject.like.repository.LikeRepository;
import study.boardProject.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public void writeComment(Long postId, CommentRequest commentRequest, User user) {
        validatePostId(postId);
        Comment comment = commentRequest.toEntity(postId, user.getLoginId());
        commentRepository.save(comment);
    }

    private void validatePostId(Long postId) {
        if(!postRepository.existsById(postId))
            throw new IllegalArgumentException();
    }

    @Override
    @Transactional
    public void modifyComment(Long commentId, CommentRequest commentRequest, User user) {
        Comment comment = commentRepository.findCommentByIdAndUserLoginId(commentId, user.getLoginId()).orElseThrow(IllegalArgumentException::new);
        comment.editComment(commentRequest.getContent());
        commentRepository.saveAndFlush(comment);
    }

    @Override
    @Transactional
    public void deleteCommentAndBelongs(Long commentId, User user) {
        commentRepository.deleteCommentByIdAndUserLoginId(commentId, user.getLoginId());
        likeRepository.deleteByTargetIdAndCategory(commentId, LikeCategory.COMMENT);
    }

}
