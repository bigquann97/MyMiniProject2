package sparta.spartaproject.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.spartaproject.dto.comment.CommentDto;
import sparta.spartaproject.entity.comment.Comment;
import sparta.spartaproject.entity.post.Post;
import sparta.spartaproject.entity.user.User;
import sparta.spartaproject.exception.NotExistCommentException;
import sparta.spartaproject.exception.NotExistPostException;
import sparta.spartaproject.exception.UnauthorizedException;
import sparta.spartaproject.repository.comment.CommentRepository;
import sparta.spartaproject.repository.post.PostRepository;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentDto.CommentRes writeComment(Long postId, CommentDto.CommentReq commentReq, User user) {
        Post post = postRepository.findById(postId).orElseThrow(NotExistPostException::new);
        Comment comment = CommentDto.CommentReq.toEntity(commentReq, post, user);
        commentRepository.save(comment);
        return CommentDto.CommentRes.of(comment);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(NotExistPostException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotExistCommentException::new);
        validatePostCommentUser(post, comment, user);
        commentRepository.delete(comment);
    }

    @Transactional
    public CommentDto.CommentRes modifyComment(Long postId, Long commentId, CommentDto.CommentReq commentReq, User user) {
        Post post = postRepository.findById(postId).orElseThrow(NotExistPostException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotExistCommentException::new);
        validatePostCommentUser(post, comment, user);
        comment.editComment(commentReq);
        commentRepository.saveAndFlush(comment);
        return CommentDto.CommentRes.of(comment);
    }

    private void validatePostCommentUser(Post post, Comment comment, User user) {
        if(!user.isAdmin() && !user.hasComment(comment))
            throw new UnauthorizedException();
        if(!post.hasComment(comment))
            throw new IllegalArgumentException();
    }

}
