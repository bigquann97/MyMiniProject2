package study.boardProject.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import study.boardProject.auth.entity.User;
import study.boardProject.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPostId(@NonNull Long postId, Pageable pageable);

    Page<Comment> findByUser(User user, Pageable pageable);

}
