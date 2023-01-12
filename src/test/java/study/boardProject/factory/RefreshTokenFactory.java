package study.boardProject.factory;

import study.boardProject.auth.entity.RefreshToken;

public class RefreshTokenFactory {

    public static RefreshToken buildRefreshToken() {
        return RefreshToken.builder()
                .refreshToken("refresh")
                .email("email")
                .build();
    }

}
