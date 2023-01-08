package study.boardProject.comment.service;

import study.boardProject.comment.dto.CommentRequest;
import study.boardProject.auth.entity.User;

public interface CommentService {
    void writeComment(Long postId, CommentRequest commentRequest, User user);

    void modifyComment(Long commentId, CommentRequest commentRequest, User user);

    void deleteCommentAndBelongs(Long commentId, User user);
}
