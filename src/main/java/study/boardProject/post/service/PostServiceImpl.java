package study.boardProject.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.boardProject.comment.Comment;
import study.boardProject.auth.domain.User;
import study.boardProject.comment.repository.CommentRepository;
import study.boardProject.like.repository.LikeRepository;
import study.boardProject.post.dto.PostSimpleResponse;
import study.boardProject.post.domain.Post;
import study.boardProject.post.dto.PostRequest;
import study.boardProject.post.dto.PostResponse;
import study.boardProject.post.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

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
        List<Post> posts = postRepository.findPostsByOrderByCreatedAtDesc();
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
    public void modifyPost(Long postId, PostRequest postRequest, User user) {
        Post findPost = postRepository.findPostByIdAndUserLoginId(postId, user.getLoginId()).orElseThrow(IllegalArgumentException::new);
        findPost.editPost(postRequest.getTitle(), postRequest.getContent());
        postRepository.saveAndFlush(findPost);
    }

    @Override
    @Transactional
    public void deletePostAndBelongs(Long postId, User user) {
        Post findPost = postRepository.findPostByIdAndUserLoginId(postId, user.getLoginId()).orElseThrow(IllegalArgumentException::new);
        postRepository.delete(findPost);
        commentRepository.deleteCommentsByPostId(postId); // 이게 추가가 되어야 하는 거죠
    }

}
