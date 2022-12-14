package sparta.spartaproject.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.spartaproject.dto.post.PostDto;
import sparta.spartaproject.entity.post.Post;
import sparta.spartaproject.entity.user.User;
import sparta.spartaproject.exception.NotExistPostException;
import sparta.spartaproject.exception.UnauthorizedException;
import sparta.spartaproject.repository.post.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

import static sparta.spartaproject.dto.post.PostDto.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public PostRes getOnePost(Long id) {
        Post findPost = postRepository.findById(id).orElseThrow(NotExistPostException::new);
        return PostRes.of(findPost);
    }

    @Transactional(readOnly = true)
    public List<PostRes> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        return posts.stream().map(PostRes::of).toList();
    }

    @Transactional
    public void uploadPost(PostReq postReq, User user) {
        Post post = PostReq.toEntity(postReq, user);
        postRepository.save(post);
    }

    @Transactional
    public PostRes modifyPost(Long id, PostReq postReq, User user) {
        Post findPost = postRepository.findById(id).orElseThrow(NotExistPostException::new);
        validateUserPost(user, findPost);
        findPost.editPost(postReq);
        postRepository.saveAndFlush(findPost);
        return PostRes.of(findPost);
    }

    @Transactional
    public void deletePost(Long id, User user) {
        Post findPost = postRepository.findById(id).orElseThrow(NotExistPostException::new);
        validateUserPost(user, findPost);
        postRepository.delete(findPost);
    }

    @Transactional
    public List<PostSimpleRes> findPagePost(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.stream().map(PostSimpleRes::of).collect(Collectors.toList());
    }

    private void validateUserPost(User findUser, Post findPost) {
        if (!findUser.hasPost(findPost) && !findUser.isAdmin())
            throw new UnauthorizedException();
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