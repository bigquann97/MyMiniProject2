package study.boardProject.common.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import study.boardProject.auth.dto.TokenDto;
import study.boardProject.common.auth.AuthClaims;
import study.boardProject.common.auth.AuthDto;
import study.boardProject.common.auth.AuthUtil;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil implements AuthUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 10; // 1분 )
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 20;  // 2분

    private final Key key;
    private final UserDetailsService userDetailsService;

    public JwtUtil(@Value("${jwt.secret.key}") String secretKey,
                   UserDetailsService userDetailsService
                   ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userDetailsService = userDetailsService;
    }

    @Override
    public String resolveRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public AuthDto generateAuthDto(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        Date accessExpiration = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshExpiration = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())     // payload "sub": "name"
                .claim(AUTHORITIES_KEY, authorities)      // payload "auth": "ROLE_USER"
                .setExpiration(accessExpiration)          // payload "exp": 1516239022
                .signWith(key, SignatureAlgorithm.HS512)  // header "alg": "HS512"
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(refreshExpiration)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDto.builder()
                .grantType(BEARER_PREFIX)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessExpiration.getTime())
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean validateAuthTool(String authTool) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authTool);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
        } catch (Exception e) {
            log.error("잘못된 토큰입니다.");
        }
        return false;
    }

    @Override
    public AuthClaims parseAuthClaims(String authTool) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authTool).getBody();
            return new AuthClaims(claims);
        } catch (ExpiredJwtException e) {
            Claims claims = e.getClaims();
            return new AuthClaims(claims);
        }
    }

}