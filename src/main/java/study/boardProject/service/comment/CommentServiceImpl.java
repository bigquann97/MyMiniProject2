package study.boardProject.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.boardProject.dto.comment.CommentRequest;
import study.boardProject.entity.Comment;
import study.boardProject.entity.LikeCategory;
import study.boardProject.entity.Post;
import study.boardProject.entity.User;
import study.boardProject.exception.MismatchException;
import study.boardProject.exception.AuthorizationException;
import study.boardProject.repository.CommentRepository;
import study.boardProject.repository.LikeRepository;
import study.boardProject.repository.PostRepository;

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
