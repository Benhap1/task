package com.em.tms.controller;

import com.em.tms.DTO.AuthResponse;
import com.em.tms.DTO.TokenRefreshRequest;
import com.em.tms.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Обновление токена")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Токен успешно обновлён"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для обновления токена"),
            @ApiResponse(responseCode = "401", description = "Неверный refresh token"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse refreshToken(@RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return authService.refreshToken(tokenRefreshRequest);
    }
}

