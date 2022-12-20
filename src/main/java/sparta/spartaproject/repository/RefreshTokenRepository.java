package sparta.spartaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.spartaproject.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
