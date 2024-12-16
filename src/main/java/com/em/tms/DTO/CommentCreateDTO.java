package com.em.tms.DTO;

import jakarta.validation.constraints.NotBlank;

public record CommentCreateDTO(
        @NotBlank int taskId,
        @NotBlank String content,
        @NotBlank int authorId
) {}
