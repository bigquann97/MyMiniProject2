package study.boardProject.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.boardProject.auth.entity.User;
import study.boardProject.comment.entity.Comment;
import study.boardProject.comment.repository.CommentRepository;
import study.boardProject.like.repository.LikeRepository;
import study.boardProject.post.dto.PostRequest;
import study.boardProject.post.dto.PostResponse;
import study.boardProject.post.dto.PostSimpleResponse;
import study.boardProject.post.entity.Post;
import study.boardProject.post.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

import static study.boardProject.common.exception.PostException.PostNotFoundException;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public void uploadPost(PostRequest postRequest, User user) {
        Post post = postRequest.toEntity(user);
        postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getOnePost(Long postId, int page, Pageable pageable) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        long likeCount = likeRepository.countByPost(post);
        Page<Comment> pagedComment = commentRepository.findByPostId(postId, pageable.withPage(page + 1));
        return PostResponse.of(post, pagedComment.toList(), likeCount);
    }

    @Override
    @Transactional
    public List<PostSimpleResponse> findPagePost(Pageable pageable, int page) {
        Page<Post> pages = postRepository.findAll(pageable.withPage(page + 1));
        return pages.stream().map(PostSimpleResponse::of).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void modifyPost(Long postId, PostRequest postRequest, User user) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        post.validateUser(user);
        post.editPost(postRequest.getTitle(), postRequest.getContent());
        postRepository.saveAndFlush(post);
    }

    @Override
    @Transactional
    public void deletePostAndBelongs(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        post.validateUser(user);
        postRepository.delete(post);
    }

}
