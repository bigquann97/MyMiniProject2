package study.boardProject.auth.dto;

import lombok.*;
import study.boardProject.common.auth.AuthDto;

@Getter
@RequiredArgsConstructor
@Builder
public final class TokenDto extends AuthDto {

    private final String grantType;
    private final String accessToken;
    private final String refreshToken;
    private final Long accessTokenExpiresIn;

}