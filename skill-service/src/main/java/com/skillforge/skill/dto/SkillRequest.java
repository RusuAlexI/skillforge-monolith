package com.skillforge.skill.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SkillRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 100)
    private String name;

    @Size(max = 50)
    private String category;

    @Size(max = 1000)
    private String description;
}