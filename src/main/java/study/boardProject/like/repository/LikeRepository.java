package study.boardProject.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.lang.NonNull;
import study.boardProject.like.entity.Like;
import study.boardProject.like.entity.LikeCategory;

import javax.persistence.LockModeType;
import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Lock(value = LockModeType.OPTIMISTIC)
    boolean existsByUserIdAndTargetIdAndCategory(@NonNull Long userId, @NonNull Long targetId, @NonNull LikeCategory category);

    void deleteByTargetIdAndCategory(@NonNull Long targetId, @NonNull LikeCategory category);

    long deleteByUserIdAndTargetIdAndCategory(Long userId, Long targetId, LikeCategory category);

    List<Like> findLikesByUserIdAndCategoryOrderByCreatedAt(Long id, LikeCategory category);
}
