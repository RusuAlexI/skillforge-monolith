package com.skillforge.tracking.controller;

import com.skillforge.tracking.dto.LearningSessionRequest;
import com.skillforge.tracking.dto.LearningSessionResponse;
import com.skillforge.tracking.entity.LearningSession;
import com.skillforge.tracking.service.LearningSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class LearningSessionController {

    private final LearningSessionService sessionService;

    @PostMapping("/skill/{skillId}")
    @ResponseStatus(HttpStatus.CREATED)
    public LearningSessionResponse logSession(
            @PathVariable Long skillId,
            @Valid @RequestBody LearningSessionRequest request) {
        LearningSession session = sessionService.logSession(
                skillId,
                request.getDurationMinutes(),
                request.getSessionDate(),
                request.getNotes()
        );
        return toResponse(session);
    }

    @GetMapping("/skill/{skillId}")
    public List<LearningSessionResponse> getSessionsBySkill(@PathVariable Long skillId) {
        return sessionService.findBySkillId(skillId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/skill/{skillId}/total-minutes")
    public ResponseEntity<Map<String, Integer>> getTotalMinutes(@PathVariable Long skillId) {
        Integer total = sessionService.getTotalMinutesForSkill(skillId);
        if (total == null) total = 0;
        return ResponseEntity.ok(Map.of("totalMinutes", total));
    }

    private LearningSessionResponse toResponse(LearningSession session) {
        return LearningSessionResponse.builder()
                .id(session.getId())
                .skillId(session.getSkillId())
                .durationMinutes(session.getDurationMinutes())
                .sessionDate(session.getSessionDate())
                .notes(session.getNotes())
                .createdAt(session.getCreatedAt())
                .build();
    }
}