package sparta.spartaproject.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.spartaproject.entity.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
