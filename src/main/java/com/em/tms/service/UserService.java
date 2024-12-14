package com.em.tms.service;

import com.em.tms.DTO.AuthRequest;
import com.em.tms.DTO.AuthResponse;
import com.em.tms.DTO.UserDTO;

public interface UserService {
    void registerUser(UserDTO userDTO);
    AuthResponse authenticate(AuthRequest authRequest);
}

