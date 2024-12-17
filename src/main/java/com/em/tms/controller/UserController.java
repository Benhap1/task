package com.em.tms.controller;

import com.em.tms.DTO.AuthRequest;
import com.em.tms.DTO.AuthResponse;
import com.em.tms.DTO.UserDTO;
import com.em.tms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
    public void registerUser(@Valid @RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
    }



    @Operation(summary = "Логин пользователя", description = "Этот метод используется для логина. " +
            "Метод возвращает токены, которые будут необходимы для аутентификации и выполнения CRUD в программе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход в систему"),
            @ApiResponse(responseCode = "401", description = "Неверные данные для аутентификации"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@Valid @RequestBody AuthRequest authRequest) {
        return userService.authenticate(authRequest);
    }



    @Operation(summary = "Получение пользователя по email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь с таким email не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping("/{email}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }



    @Operation(summary = "Обновление данных пользователя по email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные пользователя успешно обновлены"),
            @ApiResponse(responseCode = "404", description = "Пользователь с таким email не найден"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные пользователя"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PutMapping("/{email}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable String email, @Valid @RequestBody UserDTO userDTO) {
        userService.updateUser(email, userDTO);
    }



    @Operation(summary = "Удаление пользователя по email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно удалён"),
            @ApiResponse(responseCode = "404", description = "Пользователь с таким email не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @DeleteMapping("/{email}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
    }
}
