package com.skillforge.tracking.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class LearningSessionResponse {
    private Long id;
    private Long skillId;
    private Integer durationMinutes;
    private LocalDate sessionDate;
    private String notes;
    private LocalDateTime createdAt;
}