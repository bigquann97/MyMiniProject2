package sparta.spartaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.spartaproject.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
