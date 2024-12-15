package com.em.tms.service;

import com.em.tms.DTO.AuthResponse;
import com.em.tms.DTO.TokenRefreshRequest;

public interface AuthService {
    AuthResponse refreshToken(TokenRefreshRequest tokenRefreshRequest);
}

