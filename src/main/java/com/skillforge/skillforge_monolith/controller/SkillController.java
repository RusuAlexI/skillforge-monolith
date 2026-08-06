package com.skillforge.skillforge_monolith.controller;


import com.skillforge.skillforge_monolith.dto.request.SkillRequest;
import com.skillforge.skillforge_monolith.dto.response.SkillResponse;
import com.skillforge.skillforge_monolith.entity.Skill;
import com.skillforge.skillforge_monolith.entity.User;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/skills")
@AllArgsConstructor
public class SkillController {
    private SkillService skillService;
    private SkillMapper skillMapper;

    @GetMapping("/user")
    public Page<SkillResponse> getByUser(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = getCurrentUser().getId();
        return skillService.findByUserId(userId, pageable).map(skillMapper::toResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponse> getById(@PathVariable Long id) {
        return skillService.findByIdWithSessions(id)
                .map(skillMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public SkillResponse create(@Valid @RequestBody SkillRequest request) {
        Long userId = getCurrentUser().getId();
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

    private User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found. Authentication is null or not authenticated.");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof String) {
            throw new IllegalStateException(
                    "Principal is String '" + principal + "' instead of User. " +
                            "This means the JWT filter did not set the authentication correctly. " +
                            "Check if the Authorization header is being sent and the token is valid."
            );
        }

        if (!(principal instanceof User)) {
            throw new IllegalStateException(
                    "Principal is of unexpected type: " + principal.getClass().getName()
            );
        }

        return (User) principal;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        skillService.deleteSkill(id);
    }
}
