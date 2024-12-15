package com.em.tms.service.impl;

import com.em.tms.DTO.TaskCreateDTO;
import com.em.tms.DTO.TaskDTO;
import com.em.tms.entity.Task;
import com.em.tms.entity.User;
import com.em.tms.mapper.TaskMapper;
import com.em.tms.repository.TaskRepository;
import com.em.tms.repository.UserRepository;
import com.em.tms.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                .orElseThrow(() -> {
                    return new IllegalArgumentException("User with email " + taskCreateDTO.authorEmail() + " not found");
                });

        User assignee = userRepository.findByEmail(taskCreateDTO.assigneeEmail())
                .orElseThrow(() -> {
                    return new IllegalArgumentException("User with email " + taskCreateDTO.assigneeEmail() + " not found");
                });

        Task task = taskMapper.toEntity(taskCreateDTO, author, assignee);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }
}


