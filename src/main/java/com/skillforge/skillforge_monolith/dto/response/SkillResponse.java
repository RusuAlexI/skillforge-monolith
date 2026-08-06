package com.skillforge.skillforge_monolith.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SkillResponse {
    private Long id;
    private String name;
    private String category;
    private String description;
    private Integer totalMinutes;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }
}