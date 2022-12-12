package sparta.spartaproject.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.spartaproject.dto.CommentReq;
import sparta.spartaproject.dto.CommentRes;
import sparta.spartaproject.entity.Comment;
import sparta.spartaproject.entity.Post;
import sparta.spartaproject.entity.User;
import sparta.spartaproject.exception.InvalidTokenException;
import sparta.spartaproject.exception.NotExistPostException;
import sparta.spartaproject.exception.NotExistUserException;
import sparta.spartaproject.jwt.JwtUtil;
import sparta.spartaproject.repository.CommentRepository;
import sparta.spartaproject.repository.PostRepository;
import sparta.spartaproject.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    public CommentRes writeComment(Long postId, CommentReq commentReq, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new InvalidTokenException();
            }
            String loginId = claims.getSubject();
            Post post = postRepository.findById(postId).orElseThrow(NotExistPostException::new);
            User user = userRepository.findUserByLoginId(loginId).orElseThrow(NotExistUserException::new);
            Comment comment = Comment.of(commentReq, post, user);
            commentRepository.save(comment);
            userRepository.flush();
            postRepository.flush();
            return CommentRes.of(comment);
        } else {
            throw new InvalidTokenException();
        }



    }

    public void deleteComment(Long postId, Long commentId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new InvalidTokenException();
            }
            String loginId = claims.getSubject();

            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalStateException("댓글 없음"));
            User user = userRepository.findUserByLoginId(loginId).orElseThrow(NotExistUserException::new);
            Post post = postRepository.findById(postId).orElseThrow(NotExistPostException::new);

            if(user.hasThisComment(comment) && post.hashThisComment(comment)){
                commentRepository.deleteById(commentId);
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new InvalidTokenException();
        }


    }

    @Transactional
    public CommentRes modifyComment(Long postId, Long commentId, HttpServletRequest request, CommentReq commentReq) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new InvalidTokenException();
            }
            String loginId = claims.getSubject();

            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalStateException("댓글 없음"));
            User user = userRepository.findUserByLoginId(loginId).orElseThrow(NotExistUserException::new);
            Post post = postRepository.findById(postId).orElseThrow(NotExistPostException::new);

            if(user.hasThisComment(comment) && post.hashThisComment(comment)){
                comment.editComment(commentReq);
                commentRepository.saveAndFlush(comment);
                return CommentRes.of(comment);
            }
        } else {
            throw new InvalidTokenException();
        }
        return null;
    }
}
