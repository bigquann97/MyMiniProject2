package study.boardProject.like.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import study.boardProject.auth.entity.User;
import study.boardProject.comment.entity.Comment;
import study.boardProject.like.entity.Like;
import study.boardProject.post.entity.Post;

import java.util.Collection;

public interface LikeRepository extends JpaRepository<Like, Long> {

    void deleteByCommentIn(@NonNull Collection<Comment> comments);

    void deleteByPost(@NonNull Post post);

    long deleteByComment(@NonNull Comment comment);

    long countByComment(@NonNull Comment comment);

    long countByPost(@NonNull Post post);

    Page<Like> findByUserAndCommentNull(@NonNull User user, Pageable pageable);

    Page<Like> findByUserAndPostNull(@NonNull User user, Pageable pageable);

    void deleteByPostAndUser(Post post, User user);

    void deleteByCommentAndUser(Comment comment, User user);

    boolean existsByPostAndUser(Post post, User user);

    boolean existsByCommentAndUser(Comment comment, User user);

}
