package sparta.spartaproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.spartaproject.dto.post.PostRequest;
import sparta.spartaproject.dto.post.PostResponse;
import sparta.spartaproject.dto.post.PostSimpleResponse;
import sparta.spartaproject.entity.Post;
import sparta.spartaproject.entity.User;
import sparta.spartaproject.exception.NotExistPostException;
import sparta.spartaproject.exception.UnauthorizedException;
import sparta.spartaproject.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public PostResponse getOnePost(Long id) {
        Post findPost = postRepository.findById(id).orElseThrow(NotExistPostException::new);
        return PostResponse.of(findPost);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        return posts.stream().map(PostResponse::of).toList();
    }

    @Transactional
    public void uploadPost(PostRequest postRequest, User user) {
        Post post = postRequest.toEntity(user);
        postRepository.save(post);
    }

    @Transactional
    public PostResponse modifyPost(Long id, PostRequest postRequest, User user) {
        Post findPost = postRepository.findById(id).orElseThrow(NotExistPostException::new);
        validateUserPost(user, findPost);
        findPost.editPost(postRequest);
        postRepository.saveAndFlush(findPost);
        return PostResponse.of(findPost);
    }

    @Transactional
    public void deletePost(Long id, User user) {
        Post findPost = postRepository.findById(id).orElseThrow(NotExistPostException::new);
        validateUserPost(user, findPost);
        postRepository.delete(findPost);
    }

    @Transactional
    public List<PostSimpleResponse> findPagePost(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.stream().map(PostSimpleResponse::of).collect(Collectors.toList());
    }

    private void validateUserPost(User findUser, Post findPost) {
        if (!findUser.hasPost(findPost) && !findUser.isAdmin())
            throw new UnauthorizedException();
    }

}
