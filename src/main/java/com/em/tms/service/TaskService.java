package com.em.tms.service;

import com.em.tms.DTO.TaskCreateDTO;
import com.em.tms.DTO.TaskDTO;
import com.em.tms.DTO.TaskUpdateDTO;
import org.springframework.data.domain.Page;

public interface TaskService {

    TaskDTO createTask(TaskCreateDTO taskCreateDTO);
    TaskDTO updateTask(int taskId, TaskUpdateDTO taskUpdateDTO);

    void deleteTask(int taskId);
    TaskDTO getTaskById(int taskId);

    Page<TaskDTO> getAllTasks(String status, String priority, String authorEmail, String assigneeEmail, int page, int size);

}
