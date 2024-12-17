package com.em.tms.service.impl;

import com.em.tms.web.DTO.TaskCreateDTO;
import com.em.tms.web.DTO.TaskDTO;
import com.em.tms.web.DTO.TaskUpdateDTO;
import com.em.tms.entity.Task;
import com.em.tms.entity.TaskPriority;
import com.em.tms.entity.TaskStatus;
import com.em.tms.entity.User;
import com.em.tms.exception.ResourceNotFoundException;
import com.em.tms.exception.TaskAccessDeniedException;
import com.em.tms.mapper.TaskMapper;
import com.em.tms.repository.TaskRepository;
import com.em.tms.repository.UserRepository;
import com.em.tms.service.TaskService;
import com.em.tms.repository.TaskSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskDTO createTask(TaskCreateDTO taskCreateDTO) {

        User author = userRepository.findByEmail(taskCreateDTO.authorEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + taskCreateDTO.authorEmail() + " not found"));

        User assignee = userRepository.findByEmail(taskCreateDTO.assigneeEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + taskCreateDTO.assigneeEmail() + " not found"));

        Task task = taskMapper.toEntity(taskCreateDTO, author, assignee);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }


    @Override
    public TaskDTO updateTask(int taskId, TaskUpdateDTO taskUpdateDTO) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with ID " + taskId + " not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean isAdmin = currentUser.getRole().equals("ADMIN");

        if (isAdmin) {
            return updateTaskAsAdmin(task, taskUpdateDTO);
        }

        if (task.getAssignee().getEmail().equals(currentUserEmail)) {
            return updateTaskAsUser(task, taskUpdateDTO);
        }

        throw new TaskAccessDeniedException("User is not allowed to update this task");
    }

    private TaskDTO updateTaskAsAdmin(Task task, TaskUpdateDTO taskUpdateDTO) {

        User author = null;
        if (taskUpdateDTO.authorEmail() != null) {
            author = userRepository.findByEmail(taskUpdateDTO.authorEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("User with email " + taskUpdateDTO.authorEmail() + " not found"));
        }

        User assignee = null;
        if (taskUpdateDTO.assigneeEmail() != null) {
            assignee = userRepository.findByEmail(taskUpdateDTO.assigneeEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("User with email " + taskUpdateDTO.assigneeEmail() + " not found"));
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
                .orElseThrow(() -> new ResourceNotFoundException("Task with ID " + taskId + " not found"));
        taskRepository.delete(task);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDTO getTaskById(int taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with ID " + taskId + " not found"));
        return taskMapper.toDto(task);
    }

    @Transactional(readOnly = true)
    public Page<TaskDTO> getAllTasks(String status, String priority, String authorEmail, String assigneeEmail, int page, int size) {

        Specification<Task> specification = Specification.where(null);

        if (status != null) {
            specification = specification.and(TaskSpecifications.hasStatus(TaskStatus.valueOf(status)));
        }

        if (priority != null) {
            specification = specification.and(TaskSpecifications.hasPriority(TaskPriority.valueOf(priority)));
        }

        if (authorEmail != null) {
            specification = specification.and(TaskSpecifications.hasAuthorEmail(authorEmail));
        }

        if (assigneeEmail != null) {
            specification = specification.and(TaskSpecifications.hasAssigneeEmail(assigneeEmail));
        }

        Pageable pageable = PageRequest.of(page, size);

        return taskRepository.findAll(specification, pageable)
                .map(taskMapper::toDto);
    }
}



