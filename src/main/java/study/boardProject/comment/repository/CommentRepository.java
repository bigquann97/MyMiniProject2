package study.boardProject.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import study.boardProject.comment.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteCommentsByPostId(@NonNull Long postId);

    List<Comment> findCommentsByPostIdOrderByCreatedAtDesc(Long postId);

    void deleteCommentByIdAndUserLoginId(Long id, String userLoginId);

    Optional<Comment> findCommentByIdAndUserLoginId(Long id, String userLoginId);

    List<Comment> findCommentsByUserLoginIdOrderByCreatedAtDesc(String loginId);
}
