package com.skillforge.skillforge_monolith.controller;


import com.skillforge.skillforge_monolith.dto.request.SkillRequest;
import com.skillforge.skillforge_monolith.dto.response.SkillResponse;
import com.skillforge.skillforge_monolith.entity.Skill;
import com.skillforge.skillforge_monolith.mapper.SkillMapper;
import com.skillforge.skillforge_monolith.service.SkillService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/skills")
@AllArgsConstructor
public class SkillController {
    private SkillService skillService;
    private SkillMapper skillMapper;

    @GetMapping("/user/{userId}")
    public Page<SkillResponse> getByUser(
            @PathVariable Long userId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return skillService.findByUserId(userId, pageable).map(skillMapper::toResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponse> getById(@PathVariable Long id) {
        return skillService.findByIdWithSessions(id)
                .map(skillMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public SkillResponse create(@PathVariable Long userId, @Valid @RequestBody SkillRequest request) {
        Skill skill = skillService.createSkill(
                userId, request.getName(), request.getCategory(), request.getDescription());
        return skillMapper.toResponse(skill);
    }

    @PutMapping("/{id}")
    public SkillResponse update(@PathVariable Long id, @Valid @RequestBody SkillRequest request) {
        Skill skill = skillService.updateSkill(
                id, request.getName(), request.getCategory(), request.getDescription());
        return skillMapper.toResponse(skill);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        skillService.deleteSkill(id);
    }
}
