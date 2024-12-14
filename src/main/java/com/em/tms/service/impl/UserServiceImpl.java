package com.em.tms.service.impl;

import com.em.tms.DTO.AuthRequest;
import com.em.tms.DTO.AuthResponse;
import com.em.tms.DTO.UserDTO;
import com.em.tms.entity.User;
import com.em.tms.mapper.UserMapper;
import com.em.tms.repository.UserRepository;
import com.em.tms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;


    @Override
    public void registerUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.email())) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

        return new AuthResponse(accessToken, refreshToken);
    }
}

