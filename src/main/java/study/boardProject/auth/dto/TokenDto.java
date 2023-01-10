package study.boardProject.auth.dto;

import lombok.Builder;
import lombok.Getter;
import study.boardProject.common.auth.AuthDto;

@Getter
public final class TokenDto implements AuthDto {

    private final String grantType;
    private final String accessToken;
    private final String refreshToken;
    private final Long accessTokenExpiresIn;

    @Builder
    public TokenDto(String grantType, String accessToken, String refreshToken, Long accessTokenExpiresIn) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
    }

}