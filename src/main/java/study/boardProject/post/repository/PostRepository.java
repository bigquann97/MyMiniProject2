package study.boardProject.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import study.boardProject.auth.entity.User;
import study.boardProject.post.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Transactional
    @Modifying
    @Query("delete from Post p where p.user = ?1")
    void deleteByUserQuery(@NonNull User user);

    long deleteByUser(@NonNull User user);

    Page<Post> findByUser(User user, Pageable pageable);

    List<Post> findByUser(User user);

}