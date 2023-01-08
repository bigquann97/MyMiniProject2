package study.boardProject.user.service;

import study.boardProject.comment.dto.CommentResponse;
import study.boardProject.post.dto.PostSimpleResponse;
import study.boardProject.auth.entity.User;

import java.util.List;

public interface UserService {
    List<PostSimpleResponse> getMyPosts(User user);

    List<CommentResponse> getMyComments(User user);

    List<PostSimpleResponse> getMyLikePosts(User user);

    List<CommentResponse> getMyLikeComments(User user);
}
