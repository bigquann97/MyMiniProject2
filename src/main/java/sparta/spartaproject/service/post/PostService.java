package sparta.spartaproject.service.post;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.spartaproject.config.jwt.JwtUtil;
import sparta.spartaproject.dto.*;
import sparta.spartaproject.entity.post.Post;
import sparta.spartaproject.entity.user.User;
import sparta.spartaproject.exception.InvalidTokenException;
import sparta.spartaproject.exception.NotExistPostException;
import sparta.spartaproject.exception.NotExistUserException;
import sparta.spartaproject.exception.UnauthorizedException;
import sparta.spartaproject.repository.post.PostRepository;
import sparta.spartaproject.repository.user.UserRepository;

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

    public void uploadPost(PostReq postReq, User user) {
        Post post = Post.of(postReq, user);
        postRepository.save(post);
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

            if (findUser.isAdmin() || findUser.hasPost(findPost)) {
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

            if (findUser.hasPost(findPost)) {
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


/*
    public void uploadPost(PostReq postReq, User user) {
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

 */