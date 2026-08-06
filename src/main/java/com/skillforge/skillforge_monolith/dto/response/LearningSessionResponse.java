package com.skillforge.skillforge_monolith.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class LearningSessionResponse {
    private Long id;
    private String skillId;
    private String skillName;
    private Integer durationMinutes;
    private LocalDate sessionDate;
    private String notes;
    private LocalDateTime createdAt;
}
