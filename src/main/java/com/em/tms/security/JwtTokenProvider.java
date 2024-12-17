package com.em.tms.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.em.tms.exception.JwtTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private final Algorithm algorithm;

    @Value("${jwt.access-token-validity}")
    private long accessTokenValidity;

    @Value("${jwt.refresh-token-validity}")
    private long refreshTokenValidity;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret, JwtUserDetailsService jwtUserDetailsService) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String generateAccessToken(UserDetails userDetails, Map<String, Object> claims) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(accessTokenValidity);

        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiry))
                .withPayload(claims)
                .sign(algorithm);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(refreshTokenValidity);

        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiry))
                .sign(algorithm);
    }

    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (JwtTokenException e) {
            throw new JwtTokenException("Invalid JWT token: " + e.getMessage(), e);
        }
    }

    public String extractUsername(String token) {
        try {
            return decodeToken(token).getSubject();
        } catch (JwtTokenException e) {
            throw new JwtTokenException("Failed to extract username from JWT token: " + e.getMessage(), e);
        }
    }

    public Date extractExpiration(String token) {
        try {
            return decodeToken(token).getExpiresAt();
        } catch (JwtTokenException e) {
            throw new JwtTokenException("Failed to extract expiration date from JWT token: " + e.getMessage(), e);
        }
    }

    private DecodedJWT decodeToken(String token) {
        try {
            return JWT.require(algorithm)
                    .build()
                    .verify(token);
        } catch (Exception e) {
            throw new JwtTokenException("JWT token decoding failed: " + e.getMessage(), e);
        }
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = extractExpiration(token);
        return expirationDate.before(new Date());
    }
}