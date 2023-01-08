package study.boardProject.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public final class TokenResponse {

    private final String accessToken;
    private final String refreshToken;

    public static TokenResponse of(TokenDto tokenDto) {
        return TokenResponse.builder()
                .accessToken("Bearer " + tokenDto.getAccessToken())
                .refreshToken(tokenDto.getRefreshToken())
                .build();
    }

}
