package study.boardProject.common.auth;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface AuthUtil {
    String resolveAuthTool(HttpServletRequest request);

    AuthDto generateAuthDto(Authentication authentication);

    Authentication createAuthentication(String username);

    boolean validateAuthTool(String token);

    AuthClaims parseAuthClaims(String accessToken);
}
