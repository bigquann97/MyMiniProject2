package study.boardProject.factory;

import study.boardProject.auth.entity.User;
import study.boardProject.comment.entity.Comment;

public class CommentFactory {

    public static Comment buildComment() {
        return Comment.builder()
                .user(UserFactory.buildUser1())
                .content("content")
                .parent(null)
                .build();
    }

    public static Comment buildReply() {
        return Comment.builder()
                .parent(buildComment())
                .user(UserFactory.buildUser1())
                .content("content")
                .build();
    }

    public static Comment buildCommentByUser(User user) {
        return Comment.builder()
                .user(user)
                .content("content")
                .parent(null)
                .build();
    }

    public static Comment buildReplyByComment(Comment comment) {
        return Comment.builder()
                .user(UserFactory.buildUser1())
                .content("content")
                .parent(comment)
                .build();
    }

    public static Comment buildReplyByUserAndComment(User user, Comment comment) {
        return Comment.builder()
                .user(user)
                .content("content")
                .parent(comment)
                .build();
    }

}
