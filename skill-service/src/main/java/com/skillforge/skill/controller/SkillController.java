package com.skillforge.skill.controller;

import com.skillforge.skill.dto.SkillRequest;
import com.skillforge.skill.dto.SkillResponse;
import com.skillforge.skill.entity.Skill;
import com.skillforge.skill.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @GetMapping
    public List<SkillResponse> getAllSkills(@RequestHeader("X-User-Id") Long userId) {
        return skillService.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SkillResponse getSkillById(@PathVariable Long id) {
        return toResponse(skillService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SkillResponse createSkill(@RequestHeader("X-User-Id") Long userId,
                                     @Valid @RequestBody SkillRequest request) {
        Skill skill = skillService.createSkill(
                userId, request.getName(), request.getCategory(), request.getDescription());
        return toResponse(skill);
    }

    @PutMapping("/{id}")
    public SkillResponse updateSkill(@PathVariable Long id,
                                     @Valid @RequestBody SkillRequest request) {
        Skill skill = skillService.updateSkill(
                id, request.getName(), request.getCategory(), request.getDescription());
        return toResponse(skill);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
    }

    private SkillResponse toResponse(Skill skill) {
        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .category(skill.getCategory())
                .description(skill.getDescription())
                .userId(skill.getUserId())
                .createdAt(skill.getCreatedAt())
                .build();
    }
}