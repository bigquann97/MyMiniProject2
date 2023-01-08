package study.boardProject.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.boardProject.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostsByOrderByCreatedAtDesc();

    Optional<Post> findPostByIdAndUserLoginId(Long postId, String userLoginId);

    List<Post> findPostsByUserLoginIdOrderByCreatedAtDesc(String userLoginId);
}