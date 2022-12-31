package sparta.spartaproject.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.spartaproject.dto.comment.CommentRequest;
import sparta.spartaproject.entity.Comment;
import sparta.spartaproject.entity.Post;
import sparta.spartaproject.entity.User;
import sparta.spartaproject.exception.MismatchException;
import sparta.spartaproject.exception.AuthorizationException;
import sparta.spartaproject.repository.CommentRepository;
import sparta.spartaproject.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public void writeComment(Long postId, CommentRequest commentRequest, User user) {
        Comment comment = commentRequest.toEntity(postId, user.getLoginId());
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteCommentAndBelongs(Long postId, Long commentId, User user) {
        Comment comment = validateIfCommentWrittenByUserAndBelongToPost(postId, commentId, user);
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public void modifyComment(Long postId, Long commentId, CommentRequest commentRequest, User user) {
        Comment comment = validateIfCommentWrittenByUserAndBelongToPost(postId, commentId, user);
        comment.editComment(commentRequest.getContent());
        commentRepository.saveAndFlush(comment);
    }

    private Comment validateIfCommentWrittenByUserAndBelongToPost(Long postId, Long commentId, User findUser) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);
        if (!comment.isWrittenByFindUser(findUser))
            throw new AuthorizationException();
        if (!comment.isBelongToPost(post))
            throw new MismatchException();
        return comment;
    }

}
