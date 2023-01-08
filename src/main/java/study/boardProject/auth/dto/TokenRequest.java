package study.boardProject.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public final class TokenRequest {

    private final String accessToken;
    private final String refreshToken;

}
