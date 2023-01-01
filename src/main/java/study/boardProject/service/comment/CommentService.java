package study.boardProject.service.comment;

import study.boardProject.dto.comment.CommentRequest;
import study.boardProject.entity.User;

public interface CommentService {
    public void writeComment(Long postId, CommentRequest commentRequest, User user);

    public void modifyComment(Long commentId, CommentRequest commentRequest, User user);

    public void deleteCommentAndBelongs(Long commentId, User user);
}
