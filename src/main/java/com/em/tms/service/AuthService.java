package com.em.tms.service;

import com.em.tms.web.DTO.AuthResponse;
import com.em.tms.web.DTO.TokenRefreshRequest;

public interface AuthService {
    AuthResponse refreshToken(TokenRefreshRequest tokenRefreshRequest);
}

