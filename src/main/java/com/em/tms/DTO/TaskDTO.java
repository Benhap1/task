package com.em.tms.DTO;

import com.em.tms.entity.TaskPriority;
import com.em.tms.entity.TaskStatus;

import java.time.LocalDateTime;

public record TaskDTO(
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        int authorId,
        Integer assigneeId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
