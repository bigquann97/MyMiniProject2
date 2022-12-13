package sparta.spartaproject.repository.refreshToken;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.spartaproject.entity.user.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByKey(String key);
}
