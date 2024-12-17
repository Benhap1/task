package com.em.tms.web.DTO;

public record TaskUpdateDTO(
        String title,
        String description,
        String status,
        String priority,
        String authorEmail,
        String assigneeEmail
) {}
