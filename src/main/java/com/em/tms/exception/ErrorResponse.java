package com.em.tms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private List<String> details;

    public static ErrorResponse of(String message, String detail) {
        return new ErrorResponse(message, List.of(detail));
    }
}

