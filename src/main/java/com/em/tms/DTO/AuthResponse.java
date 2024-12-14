package com.em.tms.DTO;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}
