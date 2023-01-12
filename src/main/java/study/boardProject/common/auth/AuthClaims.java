package study.boardProject.common.auth;

import io.jsonwebtoken.Claims;


public class AuthClaims {

    private final String subject;

    public AuthClaims(Claims claims) {
        this.subject = claims.getSubject();
    }

    public AuthClaims() {
        this.subject = "subject";
    }

    public String getSubject() {
        return this.subject;
    }

}
