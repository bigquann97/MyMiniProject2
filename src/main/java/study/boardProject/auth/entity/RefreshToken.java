package study.boardProject.auth.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
@Entity
public class RefreshToken {

    @Id
    private String email;

    private String refreshToken;

    @Builder
    public RefreshToken(String email, String refreshToken) {
        this.email = email;
        this.refreshToken = refreshToken;
    }

    public RefreshToken updateValue(String token) {
        this.refreshToken = token;
        return this;
    }

    public boolean validateOwner(String refreshToken) {
        return this.refreshToken.equals(refreshToken);
    }

}