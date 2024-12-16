package com.em.tms.mapper;

import com.em.tms.DTO.TaskCreateDTO;
import com.em.tms.DTO.TaskDTO;
import com.em.tms.DTO.TaskUpdateDTO;
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
        return Task.builder()
                .title(taskCreateDTO.title())
                .description(taskCreateDTO.description())
                .status(TaskStatus.valueOf(taskCreateDTO.status()))
                .priority(TaskPriority.valueOf(taskCreateDTO.priority()))
                .author(author)
                .assignee(assignee)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


    public Task updateEntity(Task task, TaskUpdateDTO taskUpdateDTO, User author, User assignee) {

        if (taskUpdateDTO.title() != null) {
            task.setTitle(taskUpdateDTO.title());
        }
        if (taskUpdateDTO.description() != null) {
            task.setDescription(taskUpdateDTO.description());
        }
        if (taskUpdateDTO.status() != null) {
            task.setStatus(TaskStatus.valueOf(taskUpdateDTO.status()));
        }
        if (taskUpdateDTO.priority() != null) {
            task.setPriority(TaskPriority.valueOf(taskUpdateDTO.priority()));
        }
        if (author != null) {
            task.setAuthor(author);
        }
        if (assignee != null) {
            task.setAssignee(assignee);
        }

        task.setUpdatedAt(LocalDateTime.now());

        return task;
    }
}
