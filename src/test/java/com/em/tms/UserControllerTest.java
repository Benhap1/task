package com.em.tms;

import com.em.tms.web.DTO.AuthRequest;
import com.em.tms.web.DTO.AuthResponse;
import com.em.tms.web.DTO.UserDTO;
import com.em.tms.web.controller.UserController;
import com.em.tms.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void registerUser_ShouldReturn201_WhenUserIsValid() throws Exception {
        UserDTO userDTO = new UserDTO("test@example.com", "password123", "USER");

        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated());

        verify(userService, times(1)).registerUser(any(UserDTO.class));
    }


    @Test
    void login_ShouldReturn200_WhenCredentialsAreValid() throws Exception {
        AuthRequest authRequest = new AuthRequest("test@example.com", "password123");
        AuthResponse authResponse = new AuthResponse("accessToken", "refreshToken");

        when(userService.authenticate(any(AuthRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/api/users/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken"));

        verify(userService, times(1)).authenticate(any(AuthRequest.class));
    }

    @Test
    void getUserByEmail_ShouldReturn200_WhenUserExists() throws Exception {
        UserDTO userDTO = new UserDTO("test@example.com", "password123", "USER");

        when(userService.getUserByEmail(anyString())).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/{email}", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.role").value("USER"));

        verify(userService, times(1)).getUserByEmail(anyString());
    }


    @Test
    void updateUser_ShouldReturn200_WhenUserIsUpdated() throws Exception {
        UserDTO userDTO = new UserDTO("test@example.com", "password123", "USER");

        mockMvc.perform(put("/api/users/{email}", "test@example.com")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());

        verify(userService, times(1)).updateUser(anyString(), any(UserDTO.class));
    }


    @Test
    void deleteUser_ShouldReturn200_WhenUserIsDeleted() throws Exception {
        mockMvc.perform(delete("/api/users/{email}", "test@example.com"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(anyString());
    }
}
