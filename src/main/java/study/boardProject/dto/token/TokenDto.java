package study.boardProject.dto.token;

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