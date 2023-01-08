package study.boardProject.like.service;

import study.boardProject.auth.entity.User;

public interface LikeService {
    void addLikeOnPost(Long postId, User user);

    void cancelLikeOnPost(Long postId, User user);

    void addLikeOnComment(Long commentId, User user);

    void cancelLikeOnComment(Long commentId, User user);
}
