package sparta.spartaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.spartaproject.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
}
