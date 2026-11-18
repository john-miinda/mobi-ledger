
package com.mobiledger.backend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.expiration-ms:900000}")
    private long expirationMs;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Long userId) {
        long currentTime = System.currentTimeMillis();

        Date issuedAt = new Date(currentTime);
        Date expiration = new Date(currentTime + expirationMs);

        SecretKey signingKey = Keys.hmacShaKeyFor(
            Decoders.BASE64.decode(secret)
        );

        return Jwts.builder()
            .subject(userId.toString())
            .issuedAt(issuedAt)
            .expiration(expiration)
            .signWith(signingKey)
            .compact();
    }

    public Long extractUserId(String token) {
        SecretKey signingKey = Keys.hmacShaKeyFor(
            Decoders.BASE64.decode(secret)
        );

        Claims claims = Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();

        return Long.parseLong(claims.getSubject());
    }
}
