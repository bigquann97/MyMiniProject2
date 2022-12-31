package study.boardProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import study.boardProject.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteCommentsByPostId(@NonNull Long postId);
    List<Comment> findCommentsByPostIdOrderByCreatedAtDesc(Long postId);

}
