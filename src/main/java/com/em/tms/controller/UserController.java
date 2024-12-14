package com.em.tms.controller;

import com.em.tms.DTO.AuthResponse;
import com.em.tms.DTO.AuthRequest;
import com.em.tms.DTO.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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


//    @GetMapping
//    @Secured("ROLE_ADMIN")
//    @ResponseStatus(HttpStatus.OK)
//    public Object getAllUsers(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return userService.getAllUsers(page, size);
//    }
//
//
//    @GetMapping("/{id}")
//    @Secured("ROLE_ADMIN")
//    @ResponseStatus(HttpStatus.OK)
//    public UserDTO getUserById(@PathVariable Long id) {
//        return userService.getUserById(id);
//    }
//
//
//    @PutMapping("/{id}")
//    @Secured("ROLE_ADMIN")
//    @ResponseStatus(HttpStatus.OK)
//    public String updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
//        userService.updateUser(id, userDTO);
//        return "User updated successfully";
//    }
//
//    @DeleteMapping("/{id}")
//    @Secured("ROLE_ADMIN")
//    @ResponseStatus(HttpStatus.OK)
//    public String deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return "User deleted successfully";
//    }
}
