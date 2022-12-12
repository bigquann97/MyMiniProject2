package sparta.spartaproject.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import sparta.spartaproject.dto.*;
import sparta.spartaproject.entity.Post;
import sparta.spartaproject.entity.User;
import sparta.spartaproject.exception.InvalidTokenException;
import sparta.spartaproject.exception.NotExistPostException;
import sparta.spartaproject.exception.NotExistUserException;
import sparta.spartaproject.exception.UnauthorizedException;
import sparta.spartaproject.jwt.JwtUtil;
import sparta.spartaproject.repository.PostRepository;
import sparta.spartaproject.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    public PostRes getOnePost(Long id) {
        Post findPost = postRepository.findById(id).orElseThrow(NotExistPostException::new);
        return PostRes.of(findPost);
    }

    public List<PostRes> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        return posts.stream().map(PostRes::of).toList();
    }

    public void uploadPost(PostReq postReq, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new InvalidTokenException();
            }
            String loginId = claims.getSubject();
            User user = userRepository.findUserByLoginId(loginId).orElseThrow(() -> new AccessDeniedException("잘못된 유저"));
            Post post = Post.of(postReq, user);
            postRepository.save(post);
        } else {
            throw new InvalidTokenException();
        }
    }

    public void deletePost(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new InvalidTokenException();
            }
            User findUser = userRepository.findUserByLoginId(claims.getSubject()).orElseThrow(NotExistUserException::new);
            Post findPost = postRepository.findById(id).orElseThrow(NotExistPostException::new);
            if (findUser.hasThisPost(findPost)) {
                postRepository.delete(findPost);
            } else {
                throw new UnauthorizedException();
            }
        } else {
            throw new InvalidTokenException();
        }
    }

    public PostRes modifyPost(Long id, PostReq postReq, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new InvalidTokenException();
            }
            User findUser = userRepository.findUserByLoginId(claims.getSubject()).orElseThrow(NotExistUserException::new);
            Post findPost = postRepository.findById(id).orElseThrow(NotExistPostException::new);

            if (findUser.hasThisPost(findPost)) {
                findPost.editPost(postReq);
                postRepository.saveAndFlush(findPost);
                return PostRes.of(findPost);
            } else {
                throw new UnauthorizedException();
            }
        }
        throw new InvalidTokenException();
    }
}
