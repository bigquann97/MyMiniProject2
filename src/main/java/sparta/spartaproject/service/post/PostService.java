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

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public PostDto.PostRes getOnePost(Long id) {
        Post findPost = postRepository.findById(id).orElseThrow(NotExistPostException::new);
        return PostDto.PostRes.of(findPost);
    }

    @Transactional(readOnly = true)
    public List<PostDto.PostRes> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        return posts.stream().map(PostDto.PostRes::of).toList();
    }

    @Transactional
    public void uploadPost(PostDto.PostReq postReq, User user) {
        Post post = PostDto.PostReq.toEntity(postReq, user);
        postRepository.save(post);
    }

    @Transactional
    public PostDto.PostRes modifyPost(Long id, PostDto.PostReq postReq, User user) {
        Post findPost = postRepository.findById(id).orElseThrow(NotExistPostException::new);
        validateAction(user, findPost);
        findPost.editPost(postReq);
        postRepository.saveAndFlush(findPost);
        return PostDto.PostRes.of(findPost);
    }

    @Transactional
    public void deletePost(Long id, User user) {
        Post findPost = postRepository.findById(id).orElseThrow(NotExistPostException::new);
        validateAction(user, findPost);
        postRepository.delete(findPost);
    }

    private void validateAction(User findUser, Post findPost) {
        if (!findUser.hasPost(findPost) && !findUser.isAdmin())
            throw new UnauthorizedException();
    }

    public List<PostDto.PostSimpleRes> findPagePost(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        List<PostDto.PostSimpleRes> data = posts.stream().map(PostDto.PostSimpleRes::of).collect(Collectors.toList());
        return data;
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