package com.shoppingmall.boot.utils;

import com.shoppingmall.boot.exception.RestException;
import com.shoppingmall.user.dto.response.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .setIssuedAt(new Date())
                .setIssuer("Unknown")
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰입니다. msg=" + e.getMessage());
            throw new ExpiredJwtException(null, null, "만료된 토큰입니다. msg=" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 토큰 형식입니다. msg=" + e.getMessage());
            throw new UnsupportedJwtException("지원하지 않는 토큰 형식입니다. msg=" + e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("유효하지 않는 토큰입니다. msg=" + e.getMessage());
            throw new MalformedJwtException("유효하지 않는 토큰입니다. msg=" + e.getMessage());
        } catch (SignatureException e) {
            log.error("유효하지 않은 Signature 입니다. msg=" + e.getMessage());
            throw new SignatureException("유효하지 않은 Signature 입니다. msg=" + e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("유효하지 않은 인자입니다. msg=" + e.getMessage());
            throw new IllegalArgumentException("유효하지 않은 인자입니다. msg=" + e.getMessage());
        }
    }
}
