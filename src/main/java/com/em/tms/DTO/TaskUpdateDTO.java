package com.em.tms.DTO;

public record TaskUpdateDTO(
        Integer id,
        String title,
        String description,
        String status,
        String priority,
        String authorEmail,
        String assigneeEmail
) {}
