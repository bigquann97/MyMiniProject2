package sparta.spartaproject.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.spartaproject.entity.post.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
}
