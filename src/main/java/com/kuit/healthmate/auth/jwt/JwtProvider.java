package com.kuit.healthmate.auth.jwt;

import static com.kuit.healthmate.global.response.ExceptionResponseStatus.INVALID_TOKEN;
import static com.kuit.healthmate.global.response.ExceptionResponseStatus.MALFORMED_TOKEN;
import static com.kuit.healthmate.global.response.ExceptionResponseStatus.UNSUPPORTED_TOKEN_TYPE;

import com.kuit.healthmate.global.exception.CustomJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtProvider {

    @Value("${secret.jwt-secret-key}")
    private String JWT_SECRET_KEY;

    @Value("${secret.jwt-expired-in}")
    private long JWT_EXPIRED_IN;

    public String createToken(String principal, long userId) {

        Date now = new Date();
        Date validity = new Date(now.getTime() + JWT_EXPIRED_IN);

        return Jwts.builder()
                .subject(principal)
                .issuedAt(now)
                .expiration(validity)
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    public boolean isExpiredToken(String token) throws CustomJwtException {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);

            return claims.getBody().getExpiration().before(new Date());

        } catch (ExpiredJwtException e) {
            return true;

        } catch (UnsupportedJwtException e) {
            throw new CustomJwtException(UNSUPPORTED_TOKEN_TYPE);
        } catch (MalformedJwtException e) {
            throw new CustomJwtException(MALFORMED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new CustomJwtException(INVALID_TOKEN);
        } catch (JwtException e) {
            log.error("[JwtTokenProvider.validateAccessToken]", e);
            throw e;
        }
    }

    public String getPrincipal(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET_KEY).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Long getUserId(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET_KEY).build()
                .parseSignedClaims(token)
                .getPayload()
                .get("userId", Long.class);
    }
}
