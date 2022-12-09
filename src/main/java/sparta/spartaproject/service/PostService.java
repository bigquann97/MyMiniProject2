package sparta.spartaproject.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import sparta.spartaproject.dto.DeleteRequestDto;
import sparta.spartaproject.entity.Post;
import sparta.spartaproject.entity.User;
import sparta.spartaproject.jwt.JwtUtil;
import sparta.spartaproject.repository.PostRepository;
import sparta.spartaproject.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    public void getOnePost(Long id) {

    }

    public void getAllPosts() {
    }

    public void uploadPost(DeleteRequestDto deleteRequestDto, HttpServletRequest request) {

    }

    public void deletePost(Long id, DeleteRequestDto deleteRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException();
            }
            User findUser = userRepository.findUserByLoginId(claims.getSubject()).orElseThrow(() -> new IllegalArgumentException("없는 유저"));
            Post findPost = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없는 게시물"));

            if (findUser.hasThisPost(findPost)) {
                postRepository.delete(findPost);
            } else {
                throw new AccessDeniedException("게시물 소유자 아님");
            }
        }
    }

}
