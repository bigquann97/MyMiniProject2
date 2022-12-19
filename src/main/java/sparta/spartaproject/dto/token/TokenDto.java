package sparta.spartaproject.dto.token;

import lombok.*;

@Getter
@RequiredArgsConstructor
@Builder
public final class TokenDto {
    private final String grantType;
    private final String accessToken;
    private final String refreshToken;
    private final Long accessTokenExpiresIn;

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static final class TokenRequest {
        private final String accessToken;
        private final String refreshToken;
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static final class TokenResponse {
        private final String accessToken;
        private final String refreshToken;

        public static TokenResponse of(TokenDto tokenDto) {
            return TokenResponse.builder()
                    .accessToken(tokenDto.getAccessToken())
                    .refreshToken(tokenDto.getRefreshToken())
                    .build();
        }
    }
}