package com.em.tms.web.DTO;

public record AuthResponse(
         String accessToken,
         String refreshToken
) {}
