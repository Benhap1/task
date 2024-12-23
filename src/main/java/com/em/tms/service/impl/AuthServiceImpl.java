package com.em.tms.service.impl;

import com.em.tms.web.DTO.AuthResponse;
import com.em.tms.web.DTO.TokenRefreshRequest;
import com.em.tms.exception.InvalidTokenException;
import com.em.tms.security.JwtTokenProvider;
import com.em.tms.security.JwtUserDetailsService;
import com.em.tms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Override
    public AuthResponse refreshToken(TokenRefreshRequest tokenRefreshRequest) {
        String refreshToken = tokenRefreshRequest.refreshToken();

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new InvalidTokenException("Invalid or expired refresh token");
        }

        String email = jwtTokenProvider.extractUsername(refreshToken);

        var userDetails = jwtUserDetailsService.loadUserByUsername(email);

        String newAccessToken = jwtTokenProvider.generateAccessToken(userDetails, Map.of());
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

        return new AuthResponse(newAccessToken, newRefreshToken);
    }
}
