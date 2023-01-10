package study.boardProject.auth.dto;

import lombok.*;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public final class TokenRequest {

    private final String accessToken;
    private final String refreshToken;

}
