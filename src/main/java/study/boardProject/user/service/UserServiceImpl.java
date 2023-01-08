package study.boardProject.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.boardProject.auth.entity.User;
import study.boardProject.comment.dto.CommentResponse;
import study.boardProject.comment.entity.Comment;
import study.boardProject.comment.repository.CommentRepository;
import study.boardProject.like.entity.Like;
import study.boardProject.like.repository.LikeRepository;
import study.boardProject.post.dto.PostSimpleResponse;
import study.boardProject.post.entity.Post;
import study.boardProject.post.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Override
    public List<PostSimpleResponse> getMyPosts(int page, Pageable pageable, User user) {
        Page<Post> pagedPosts = postRepository.findByUser(user, pageable.withPage(page));
        return pagedPosts.stream().map(PostSimpleResponse::of).collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> getMyComments(int page, Pageable pageable, User user) {
        Page<Comment> pagedComment = commentRepository.findByUser(user, pageable.withPage(page));
        return pagedComment.stream().map(CommentResponse::of).collect(Collectors.toList());
    }

    @Override
    public List<PostSimpleResponse> getMyLikePosts(int page, Pageable pageable, User user) {
        Page<Like> pagedPostLikes = likeRepository.findByUserAndCommentNull(user, pageable.withPage(page));
        return pagedPostLikes.stream().map(x -> PostSimpleResponse.of(x.getPost())).collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> getMyLikeComments(int page, Pageable pageable, User user) {
        Page<Like> pagedCommentLikes = likeRepository.findByUserAndPostNull(user, pageable.withPage(page));
        return pagedCommentLikes.stream().map(x -> CommentResponse.of(x.getComment())).collect(Collectors.toList());
    }

}
