package study.boardProject.auth.dto;

import lombok.*;

@Getter
@RequiredArgsConstructor
@Builder
public final class TokenDto {

    private final String grantType;
    private final String accessToken;
    private final String refreshToken;
    private final Long accessTokenExpiresIn;

}