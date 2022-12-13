package sparta.spartaproject.service.comment;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.spartaproject.dto.CommentReq;
import sparta.spartaproject.dto.CommentRes;
import sparta.spartaproject.entity.comment.Comment;
import sparta.spartaproject.entity.post.Post;
import sparta.spartaproject.entity.user.User;
import sparta.spartaproject.exception.*;
import sparta.spartaproject.config.jwt.JwtUtil;
import sparta.spartaproject.repository.comment.CommentRepository;
import sparta.spartaproject.repository.post.PostRepository;
import sparta.spartaproject.repository.user.UserRepository;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public CommentRes writeComment(Long postId, CommentReq commentReq, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token != null) {
            if (jwtUtil.validateToken(token)) claims = jwtUtil.getUserInfoFromToken(token);
            else throw new InvalidTokenException();

            String loginId = claims.getSubject();
            Post post = postRepository.findById(postId).orElseThrow(NotExistPostException::new);
            User user = userRepository.findUserByLoginId(loginId).orElseThrow(NotExistUserException::new);
            Comment comment = Comment.of(commentReq, post, user);

            commentRepository.save(comment);
            return CommentRes.of(comment);
        }
        throw new InvalidTokenException();
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token != null) {
            if (jwtUtil.validateToken(token)) claims = jwtUtil.getUserInfoFromToken(token);
            else throw new InvalidTokenException();

            String loginId = claims.getSubject();
            Comment comment = commentRepository.findById(commentId).orElseThrow(NotExistCommentException::new);
            User user = userRepository.findUserByLoginId(loginId).orElseThrow(NotExistUserException::new);
            Post post = postRepository.findById(postId).orElseThrow(NotExistPostException::new);

            if (post.hasComment(comment) && (user.isAdmin() || user.hasComment(comment)))
                commentRepository.delete(comment);
            return;
        }
        throw new InvalidTokenException();
    }

    @Transactional
    public CommentRes modifyComment(Long postId, Long commentId, HttpServletRequest request, CommentReq commentReq) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token != null) {
            if (jwtUtil.validateToken(token)) claims = jwtUtil.getUserInfoFromToken(token);
            else throw new InvalidTokenException();

            String loginId = claims.getSubject();
            Comment comment = commentRepository.findById(commentId).orElseThrow(NotExistCommentException::new);
            User user = userRepository.findUserByLoginId(loginId).orElseThrow(NotExistUserException::new);
            Post post = postRepository.findById(postId).orElseThrow(NotExistPostException::new);

            if(user.hasComment(comment) && post.hasComment(comment)){
                comment.editComment(commentReq);
                commentRepository.saveAndFlush(comment);
                return CommentRes.of(comment);
            }
        }
        throw new InvalidTokenException();
    }
}
