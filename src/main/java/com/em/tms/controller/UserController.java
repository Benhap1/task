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


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@Valid @RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
        return "User registered successfully";
    }


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@Valid @RequestBody AuthRequest authRequest) {
        return userService.authenticate(authRequest);
    }


    @GetMapping("/{email}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }


    @PutMapping("/{email}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public String updateUser(@PathVariable String email, @Valid @RequestBody UserDTO userDTO) {
        userService.updateUser(email, userDTO);
        return "User updated successfully";
    }

    @DeleteMapping("/{email}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public String deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return "User deleted successfully";
    }
}
