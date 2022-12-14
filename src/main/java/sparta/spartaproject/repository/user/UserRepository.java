package sparta.spartaproject.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.spartaproject.entity.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
}
