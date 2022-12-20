package sparta.spartaproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.spartaproject.dto.comment.CommentRequest;
import sparta.spartaproject.dto.comment.CommentResponse;
import sparta.spartaproject.entity.Comment;
import sparta.spartaproject.entity.Post;
import sparta.spartaproject.entity.User;
import sparta.spartaproject.exception.CommentPostNotMathException;
import sparta.spartaproject.exception.NotExistCommentException;
import sparta.spartaproject.exception.NotExistPostException;
import sparta.spartaproject.exception.UnauthorizedException;
import sparta.spartaproject.repository.CommentRepository;
import sparta.spartaproject.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponse writeComment(Long postId, CommentRequest commentRequest, User user) {
        Post post = postRepository.findById(postId).orElseThrow(NotExistPostException::new);
        Comment comment = commentRequest.toEntity(post, user);
        commentRepository.save(comment);
        return CommentResponse.of(comment);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(NotExistPostException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotExistCommentException::new);
        validatePostCommentUser(post, comment, user);
        commentRepository.delete(comment);
    }

    @Transactional
    public CommentResponse modifyComment(Long postId, Long commentId, CommentRequest commentRequest, User user) {
        Post post = postRepository.findById(postId).orElseThrow(NotExistPostException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotExistCommentException::new);
        validatePostCommentUser(post, comment, user);
        comment.editComment(commentRequest);
        commentRepository.saveAndFlush(comment);
        return CommentResponse.of(comment);
    }

    private void validatePostCommentUser(Post post, Comment comment, User user) {
        if(!user.isAdmin() && !user.hasComment(comment))
            throw new UnauthorizedException();
        if(!post.hasComment(comment))
            throw new CommentPostNotMathException();
    }

}
