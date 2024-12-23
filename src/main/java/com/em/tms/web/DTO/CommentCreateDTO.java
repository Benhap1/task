package com.em.tms.web.DTO;

import jakarta.validation.constraints.NotBlank;

public record CommentCreateDTO(
        @NotBlank int taskId,
        @NotBlank String content,
        @NotBlank int authorId
) {}
