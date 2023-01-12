package study.boardProject.factory;

import study.boardProject.auth.entity.User;
import study.boardProject.comment.entity.Comment;
import study.boardProject.like.entity.Like;
import study.boardProject.post.entity.Post;

public class LikeFactory {

    public static Like buildPostLike() {
        return Like.builder()
                .post(PostFactory.buildPost())
                .comment(null)
                .user(UserFactory.buildUser1())
                .build();
    }

    public static Like buildPostLikeByPost(Post post) {
        return Like.builder()
                .post(post)
                .comment(null)
                .user(UserFactory.buildUser1())
                .build();
    }

    public static Like buildPostLikeByUser(User user) {
        return Like.builder()
                .post(PostFactory.buildPost())
                .comment(null)
                .user(user)
                .build();
    }

    public static Like buildPostLikeByUserAndPost(User user, Post post) {
        return Like.builder()
                .post(post)
                .comment(null)
                .user(user)
                .build();
    }

    public static Like buildCommentLike() {
        return Like.builder()
                .post(null)
                .comment(CommentFactory.buildComment())
                .user(UserFactory.buildUser1())
                .build();
    }

    public static Like buildCommentLikeByComment(Comment comment) {
        return Like.builder()
                .post(null)
                .comment(comment)
                .user(UserFactory.buildUser1())
                .build();
    }

    public static Like buildCommentLikeByUser(User user) {
        return Like.builder()
                .post(null)
                .comment(CommentFactory.buildComment())
                .user(user)
                .build();
    }

    public static Like buildCommentLikeByUserAndComment(User user, Comment comment) {
        return Like.builder()
                .post(null)
                .comment(comment)
                .user(user)
                .build();
    }


}
