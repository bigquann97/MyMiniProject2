package study.boardProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.boardProject.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
}
