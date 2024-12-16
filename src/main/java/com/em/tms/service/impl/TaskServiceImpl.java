package com.em.tms.service.impl;

import com.em.tms.DTO.TaskCreateDTO;
import com.em.tms.DTO.TaskDTO;
import com.em.tms.DTO.TaskUpdateDTO;
import com.em.tms.entity.Task;
import com.em.tms.entity.TaskStatus;
import com.em.tms.entity.User;
import com.em.tms.mapper.TaskMapper;
import com.em.tms.repository.TaskRepository;
import com.em.tms.repository.UserRepository;
import com.em.tms.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskDTO createTask(TaskCreateDTO taskCreateDTO) {

        User author = userRepository.findByEmail(taskCreateDTO.authorEmail())
                .orElseThrow(() -> new IllegalArgumentException("User with email " + taskCreateDTO.authorEmail() + " not found"));

        User assignee = userRepository.findByEmail(taskCreateDTO.assigneeEmail())
                .orElseThrow(() -> new IllegalArgumentException("User with email " + taskCreateDTO.assigneeEmail() + " not found"));

        Task task = taskMapper.toEntity(taskCreateDTO, author, assignee);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }


    @Override
    public TaskDTO updateTask(int taskId, TaskUpdateDTO taskUpdateDTO) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + taskId + " not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        boolean isAdmin = currentUser.getRole().equals("ADMIN");

        if (isAdmin) {
            return updateTaskAsAdmin(task, taskUpdateDTO);
        }

        if (task.getAssignee().getEmail().equals(currentUserEmail)) {
            return updateTaskAsUser(task, taskUpdateDTO);
        }

        throw new SecurityException("User is not allowed to update this task");
    }


    private TaskDTO updateTaskAsAdmin(Task task, TaskUpdateDTO taskUpdateDTO) {

        User author = null;
        if (taskUpdateDTO.authorEmail() != null) {
            author = userRepository.findByEmail(taskUpdateDTO.authorEmail())
                    .orElseThrow(() -> new IllegalArgumentException("User with email " + taskUpdateDTO.authorEmail() + " not found"));
        }

        User assignee = null;
        if (taskUpdateDTO.assigneeEmail() != null) {
            assignee = userRepository.findByEmail(taskUpdateDTO.assigneeEmail())
                    .orElseThrow(() -> new IllegalArgumentException("User with email " + taskUpdateDTO.assigneeEmail() + " not found"));
        }

        task = taskMapper.updateEntity(task, taskUpdateDTO, author, assignee);

        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDto(updatedTask);
    }

    private TaskDTO updateTaskAsUser(Task task, TaskUpdateDTO taskUpdateDTO) {
        if (taskUpdateDTO.status() != null) {
            task.setStatus(TaskStatus.valueOf(taskUpdateDTO.status()));
        }
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDto(updatedTask);
    }
    @Override
    public void deleteTask(int taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + taskId + " not found"));
        taskRepository.delete(task);
    }
}



