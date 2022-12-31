package study.boardProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.boardProject.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
}
