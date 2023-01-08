package study.boardProject.comment.service;

import study.boardProject.comment.dto.CommentRequest;
import study.boardProject.auth.domain.User;

public interface CommentService {
    public void writeComment(Long postId, CommentRequest commentRequest, User user);

    public void modifyComment(Long commentId, CommentRequest commentRequest, User user);

    public void deleteCommentAndBelongs(Long commentId, User user);
}
