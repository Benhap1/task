package com.em.tms.DTO;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CommentDTO(
        @NotBlank int id,
        @NotBlank int taskId,
        @NotBlank int authorId,
        @NotBlank String content,
        @NotBlank LocalDateTime createdAt
) {}
