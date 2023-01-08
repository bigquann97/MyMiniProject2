package study.boardProject.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import study.boardProject.auth.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByNickname(@NonNull String nickname);

    Optional<User> findUserByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
    
}
