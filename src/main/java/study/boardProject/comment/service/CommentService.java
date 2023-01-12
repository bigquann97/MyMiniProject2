package study.boardProject.comment.service;

import study.boardProject.auth.entity.User;
import study.boardProject.comment.dto.CommentRequest;
import study.boardProject.comment.entity.Comment;

public interface CommentService {

    void writeComment(Long postId, CommentRequest commentRequest, User user);

    void modifyComment(Long commentId, CommentRequest commentRequest, User user);

    void deleteComment(Long commentId, User user);

    void writeReply(Long parentId, CommentRequest commentRequest, User user);

    void deleteCommentAndBelongs(Comment comment);

}
