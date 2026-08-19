package com.skillforge.tracking.service;

import com.skillforge.tracking.entity.LearningSession;
import com.skillforge.tracking.repository.LearningSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LearningSessionService {

    private final LearningSessionRepository sessionRepository;

    public LearningSession logSession(Long skillId, Integer durationMinutes,
                                      LocalDate sessionDate, String notes) {
        LearningSession session = LearningSession.builder()
                .skillId(skillId)
                .durationMinutes(durationMinutes)
                .sessionDate(sessionDate)
                .notes(notes)
                .build();
        return sessionRepository.save(session);
    }

    public List<LearningSession> findBySkillId(Long skillId) {
        return sessionRepository.findBySkillId(skillId);
    }

    public Integer getTotalMinutesForSkill(Long skillId) {
        return sessionRepository.getTotalMinutesBySkillId(skillId);
    }
}