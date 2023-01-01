package study.boardProject.service.user;

import study.boardProject.dto.comment.CommentResponse;
import study.boardProject.dto.post.PostSimpleResponse;
import study.boardProject.entity.User;

import java.util.List;

public interface UserService {
    List<PostSimpleResponse> getMyPosts(User user);

    List<CommentResponse> getMyComments(User user);

    List<PostSimpleResponse> getMyLikePosts(User user);

    List<CommentResponse> getMyLikeComments(User user);
}
