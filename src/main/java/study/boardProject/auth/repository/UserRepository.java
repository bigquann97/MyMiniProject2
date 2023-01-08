package study.boardProject.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.boardProject.auth.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
}
