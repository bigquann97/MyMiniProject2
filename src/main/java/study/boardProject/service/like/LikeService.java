package study.boardProject.service.like;

import study.boardProject.entity.User;

public interface LikeService {
    void addLikeOnPost(Long postId, User user);

    void cancelLikeOnPost(Long postId, User user);

    void addLikeOnComment(Long commentId, User user);

    void cancelLikeOnComment(Long commentId, User user);
}
