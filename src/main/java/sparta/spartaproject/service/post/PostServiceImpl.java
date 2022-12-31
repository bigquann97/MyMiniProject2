package sparta.spartaproject.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.spartaproject.dto.post.PostRequest;
import sparta.spartaproject.dto.post.PostResponse;
import sparta.spartaproject.dto.post.PostSimpleResponse;
import sparta.spartaproject.entity.Comment;
import sparta.spartaproject.entity.Post;
import sparta.spartaproject.entity.User;
import sparta.spartaproject.exception.AuthorizationException;
import sparta.spartaproject.repository.CommentRepository;
import sparta.spartaproject.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public PostResponse getOnePost(Long postId) {
        Post findPost = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        List<Comment> comments = commentRepository.findCommentsByPostIdOrderByCreatedAtDesc(postId);
        return PostResponse.of(findPost, comments);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostSimpleResponse> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        return posts.stream().map(PostSimpleResponse::of).toList();
    }

    @Override
    @Transactional
    public List<PostSimpleResponse> findPagePost(Pageable pageable, int page) {
        Page<Post> posts = postRepository.findAll(pageable.withPage(page));
        return posts.stream().map(PostSimpleResponse::of).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void uploadPost(PostRequest postRequest, User user) {
        Post post = postRequest.toEntity(user);
        postRepository.save(post);
    }

    @Override
    @Transactional
    public void modifyPost(Long id, PostRequest postRequest, User user) {
        Post findPost = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        validatePostAuthor(user, findPost);
        findPost.editPost(postRequest.getTitle(), postRequest.getContent());
        postRepository.saveAndFlush(findPost);
    }

    @Override
    @Transactional
    public void deletePostAndBelongs(Long postId, User user) {
        Post findPost = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        validatePostAuthor(user, findPost);
        postRepository.delete(findPost);
        commentRepository.deleteCommentsByPostId(postId); // 이게 추가가 되어야 하는 거죠
    }

    private void validatePostAuthor(User findUser, Post findPost) {
        if (findPost.isWrittenByFindUser(findUser))
            throw new AuthorizationException();
    }

}
