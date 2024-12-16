package com.em.tms.controller;
import com.em.tms.DTO.TaskCreateDTO;
import com.em.tms.DTO.TaskDTO;
import com.em.tms.DTO.TaskUpdateDTO;
import com.em.tms.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Создание новой задачи")
    @PostMapping
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@Valid @RequestBody TaskCreateDTO taskCreateDTO) {
        return taskService.createTask(taskCreateDTO);
    }


    @Operation(summary = "Редактирование задачи по ID")
    @PutMapping("/{taskId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO updateTask(@PathVariable int taskId,
                              @Valid @RequestBody TaskUpdateDTO taskUpdateDTO) {
        return taskService.updateTask(taskId, taskUpdateDTO);
    }

    @Operation(summary = "Удаление задачи по ID")
    @DeleteMapping("/{taskId}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public String deleteTask(@PathVariable int taskId) {
        taskService.deleteTask(taskId);
        return "Task deleted successfully";
    }

    @Operation(summary = "Получение задачи по ID")
    @GetMapping("/{taskId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO getTaskById(@PathVariable int taskId) {
        return taskService.getTaskById(taskId);
    }

    @Operation(summary = "Получение всех задач с фильтрацией и пагинацией")
    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ResponseStatus(HttpStatus.OK)
    public Page<TaskDTO> getAllTasks(@RequestParam(required = false) String status,
                                     @RequestParam(required = false) String priority,
                                     @RequestParam(required = false) String authorEmail,
                                     @RequestParam(required = false) String assigneeEmail,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        return taskService.getAllTasks(status, priority, authorEmail, assigneeEmail, page, size);
    }
}
