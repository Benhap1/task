package com.em.tms.DTO;

import com.em.tms.entity.TaskPriority;
import com.em.tms.entity.TaskStatus;

import java.time.LocalDateTime;

public record TaskDTO(
        int id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        String authorEmail,
        String assigneeEmail,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
