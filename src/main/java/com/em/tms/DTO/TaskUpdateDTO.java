package com.em.tms.DTO;

public record TaskUpdateDTO(
        String title,
        String description,
        String status,
        String priority,
        String authorEmail,
        String assigneeEmail
) {}
