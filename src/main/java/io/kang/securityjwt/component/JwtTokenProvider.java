package io.kang.securityjwt.component;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

import java.util.Date;

@Slf4j
public class JwtTokenProvider {
    // 아래 내용들은 application.yml 파일에서 불러와 관리하는 것이 괜찮을 것이다.
    private static final String JWT_SECRET = "secretkey";
    private static final int JWT_EXPIRATION_MS = 60480000;

    public static String generateToken(Authentication authentication) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);
        return Jwts.builder()
                .setSubject((String) authentication.getPrincipal())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    // Token 에서 아이디 (Subject) 를 가져온다.
    public static String getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(JWT_SECRET)
                            .parseClaimsJws(token)
                            .getBody();

        return claims.getSubject();
    }

    // Token 의 유효성을 검사한다.
    public static boolean validateToken(String token) {
        if (!StringUtils.isEmpty(token)) {
            try {
                Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
                return true;
            } catch (SignatureException ex) {
                log.error("Invalid JWT signature: {}", ex.getMessage());
            } catch (MalformedJwtException ex) {
                log.error("Invalid JWT token: {}", ex.getMessage());
            } catch (ExpiredJwtException ex) {
                log.error("Expired JWT token: {}", ex.getMessage());
            } catch (UnsupportedJwtException ex) {
                log.error("Unsupported JWT token: {}", ex.getMessage());
            } catch (IllegalArgumentException ex) {
                log.error("JWT claims string is empty: {}", ex.getMessage());
            }
        }

        return false;
    }
}
