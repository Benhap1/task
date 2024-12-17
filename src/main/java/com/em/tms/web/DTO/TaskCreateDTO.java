package com.em.tms.web.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record TaskCreateDTO(
        @NotBlank String title,
        @NotBlank String description,
        @Email String authorEmail,
        @Email String assigneeEmail,
        @NotBlank String priority,
        @NotBlank String status
) {}


