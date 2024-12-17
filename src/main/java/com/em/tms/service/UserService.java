package com.em.tms.service;

import com.em.tms.web.DTO.AuthRequest;
import com.em.tms.web.DTO.AuthResponse;
import com.em.tms.web.DTO.UserDTO;

public interface UserService {
    void registerUser(UserDTO userDTO);
    AuthResponse authenticate(AuthRequest authRequest);
    UserDTO getUserByEmail(String email);
    void updateUser(String email, UserDTO userDTO);

    void deleteUser(String email);
}

