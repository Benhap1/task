package com.em.tms;

import com.em.tms.DTO.TaskCreateDTO;
import com.em.tms.DTO.TaskDTO;
import com.em.tms.DTO.TaskUpdateDTO;
import com.em.tms.controller.TaskController;
import com.em.tms.entity.TaskPriority;
import com.em.tms.entity.TaskStatus;
import com.em.tms.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDateTime;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createTask_shouldReturnCreated() throws Exception {
        TaskCreateDTO taskCreateDTO = new TaskCreateDTO(
                "Test Task",
                "Test Description",
                "author@example.com",
                "assignee@example.com",
                "HIGH",
                "PENDING"
        );

        TaskDTO taskDTO = new TaskDTO(1, "Test Task", "Test Description", TaskStatus.PENDING, TaskPriority.HIGH,
                "author@example.com", "assignee@example.com", LocalDateTime.now(), LocalDateTime.now());

        when(taskService.createTask(any(TaskCreateDTO.class))).thenReturn(taskDTO);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test Task\", \"description\": \"Test Description\", \"authorEmail\": \"author@example.com\", \"assigneeEmail\": \"assignee@example.com\", \"priority\": \"HIGH\", \"status\": \"PENDING\"}")
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateTask_shouldReturnUpdatedTask() throws Exception {
        TaskUpdateDTO taskUpdateDTO = new TaskUpdateDTO(
                "Updated Task",
                "Updated Description",
                "PENDING",
                "HIGH",
                "author@example.com",
                "assignee@example.com"
        );

        TaskDTO updatedTaskDTO = new TaskDTO(1, "Updated Task", "Updated Description", TaskStatus.PENDING, TaskPriority.HIGH,
                "author@example.com", "assignee@example.com", LocalDateTime.now(), LocalDateTime.now());

        when(taskService.updateTask(anyInt(), any(TaskUpdateDTO.class))).thenReturn(updatedTaskDTO);

        mockMvc.perform(put("/api/tasks/{taskId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Task\", \"description\": \"Updated Description\", \"status\": \"PENDING\", \"priority\": \"HIGH\", \"authorEmail\": \"author@example.com\", \"assigneeEmail\": \"assignee@example.com\"}")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteTask_shouldReturnOk() throws Exception {
        doNothing().when(taskService).deleteTask(1);

        mockMvc.perform(delete("/api/tasks/{taskId}", 1).with(csrf()))
                .andExpect(status().isOk());
        verify(taskService, times(1)).deleteTask(1);
    }

    @Test
    @WithMockUser(roles = "USER")
    void getTaskById_shouldReturnTask() throws Exception {
        TaskDTO taskDTO = new TaskDTO(1, "Test Task", "Test Description", TaskStatus.PENDING, TaskPriority.HIGH,
                "author@example.com", "assignee@example.com", LocalDateTime.now(), LocalDateTime.now());

        when(taskService.getTaskById(1)).thenReturn(taskDTO);

        mockMvc.perform(get("/api/tasks/{taskId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

}

