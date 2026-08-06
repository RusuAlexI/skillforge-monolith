package com.skillforge.skillforge_monolith.controller;

import com.skillforge.skillforge_monolith.dto.request.LearningSessionRequest;
import com.skillforge.skillforge_monolith.dto.response.LearningSessionResponse;
import com.skillforge.skillforge_monolith.entity.LearningSession;
import com.skillforge.skillforge_monolith.entity.User;
import com.skillforge.skillforge_monolith.mapper.LearningSessionMapper;
import com.skillforge.skillforge_monolith.service.LearningSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class LearningSessionController {
    private  LearningSessionService sessionService;
    private  LearningSessionMapper sessionMapper;

    @PostMapping("/skill/{skillId}")
    @ResponseStatus(HttpStatus.CREATED)
    public LearningSessionResponse logSession(
            @PathVariable Long skillId, @Valid @RequestBody LearningSessionRequest request) {
        LearningSession session = sessionService.logSession(
                skillId, request.getDurationMinutes(), request.getSessionDate(), request.getNotes());
        return sessionMapper.toResponse(session);
    }

    @GetMapping("/skill/{skillId}")
    public List<LearningSessionResponse> getBySkill(
            @PathVariable Long skillId,
            @RequestParam(defaultValue = "sessionDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return sessionMapper.toResponseList(sessionService.findBySkillId(skillId, sort));
    }

    @GetMapping("/skill/{skillId}/total-minutes")
    public ResponseEntity<Map<String, Integer>> getTotalMinutes(@PathVariable Long skillId) {
        Integer total = sessionService.getTotalMinutesForSkill(skillId);
        return ResponseEntity.ok(Map.of("totalMinutes", total));
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}