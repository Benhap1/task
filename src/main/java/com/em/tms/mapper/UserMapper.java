package com.em.tms.mapper;

import com.em.tms.web.DTO.UserDTO;
import com.em.tms.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setRole(userDTO.role());
        return user;
    }

    public UserDTO toDto(User user) {
        return new UserDTO(user.getEmail(), user.getPassword(), user.getRole());
    }
}
