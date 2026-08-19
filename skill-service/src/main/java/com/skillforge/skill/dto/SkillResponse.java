package com.skillforge.skill.dto;

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
    private Long userId;
    private LocalDateTime createdAt;
}