package study.boardProject.user.service;

import org.springframework.data.domain.Pageable;
import study.boardProject.auth.entity.User;
import study.boardProject.comment.dto.CommentResponse;
import study.boardProject.post.dto.PostSimpleResponse;

import java.util.List;

public interface UserService {

    List<PostSimpleResponse> getMyPosts(int page, Pageable pageable, User user);

    List<CommentResponse> getMyComments(int page, Pageable pageable, User user);

    List<PostSimpleResponse> getMyLikePosts(int page, Pageable pageable, User user);

    List<CommentResponse> getMyLikeComments(int i, Pageable pageable, User user);

}
