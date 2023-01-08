package study.boardProject.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class TokenRequest {

    private final String accessToken;
    private final String refreshToken;

}
