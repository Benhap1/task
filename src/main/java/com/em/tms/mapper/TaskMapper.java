package com.em.tms.mapper;

import com.em.tms.DTO.TaskCreateDTO;
import com.em.tms.DTO.TaskDTO;
import com.em.tms.entity.Task;
import com.em.tms.entity.TaskPriority;
import com.em.tms.entity.TaskStatus;
import com.em.tms.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskMapper {

    public TaskDTO toDto(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getAuthor().getEmail(),
                task.getAssignee() != null ? task.getAssignee().getEmail() : null,
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    public Task toEntity(TaskCreateDTO taskCreateDTO, User author, User assignee) {
        Task task = new Task();
        task.setTitle(taskCreateDTO.title());
        task.setDescription(taskCreateDTO.description());
        task.setPriority(TaskPriority.valueOf(taskCreateDTO.priority()));
        task.setStatus(TaskStatus.valueOf(taskCreateDTO.status()));
        task.setAuthor(author);
        task.setAssignee(assignee);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return task;
    }
}
