package com.skillforge.skillforge_monolith.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;
    @Builder.Default
    private List<FieldError> fieldErrors = new ArrayList<>();

    @Data
    @AllArgsConstructor
    public static class FieldError {
        private String field;
        private String message;
    }
}