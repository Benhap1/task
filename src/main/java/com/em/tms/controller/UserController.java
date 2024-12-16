package com.em.tms.controller;

import com.em.tms.DTO.AuthResponse;
import com.em.tms.DTO.AuthRequest;
import com.em.tms.DTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.em.tms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Этот метод используется для регистрации нового пользователя в системе. " +
                    "Доступ к этому методу имеет любой пользователь, поскольку он не требует аутентификации или определённой роли. " +
                    "После успешной регистрации пользователь может получить роль." +
                    "Роли в системе: USER (для обычных пользователей) и ADMIN (для администраторов)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные пользователя"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@Valid @RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
        return "User registered successfully";
    }

    @Operation(summary = "Логин пользователя")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@Valid @RequestBody AuthRequest authRequest) {
        return userService.authenticate(authRequest);
    }

    @Operation(summary = "Получение пользователя по email")
    @GetMapping("/{email}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }


    @Operation(summary = "Обновление данных пользователя по email")
    @PutMapping("/{email}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public String updateUser(@PathVariable String email, @Valid @RequestBody UserDTO userDTO) {
        userService.updateUser(email, userDTO);
        return "User updated successfully";
    }

    @Operation(summary = "Удаление пользователя по email")
    @DeleteMapping("/{email}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public String deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return "User deleted successfully";
    }
}
