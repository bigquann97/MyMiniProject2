package sparta.spartaproject.service.comment;

import sparta.spartaproject.dto.comment.CommentRequest;
import sparta.spartaproject.entity.User;

public interface CommentService {
    public void writeComment(Long postId, CommentRequest commentRequest, User user);

    public void modifyComment(Long postId, Long commentId, CommentRequest commentRequest, User user);

    public void deleteCommentAndBelongs(Long postId, Long commentId, User user);
}
