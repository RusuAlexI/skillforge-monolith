package com.skillforge.skillforge_monolith.service;

import com.skillforge.skillforge_monolith.entity.LearningSession;
import com.skillforge.skillforge_monolith.entity.Skill;
import com.skillforge.skillforge_monolith.repository.LearningSessionRepository;
import com.skillforge.skillforge_monolith.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class LearningSessionService {
    private LearningSessionRepository sessionRepository;
    private SkillRepository skillRepository;

    public LearningSession logSession(Long skillId, Integer durationMinutes,
                                      LocalDate sessionDate, String notes) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityNotFoundException("Skill not found: " + skillId));
        LearningSession session = LearningSession.builder()
                .skill(skill)
                .durationMinutes(durationMinutes)
                .sessionDate(sessionDate)
                .notes(notes)
                .build();
        skill.addSession(session);  // Bidirectional sync
        return sessionRepository.save(session);
    }

    @Transactional(readOnly = true)
    public List<LearningSession> findBySkillIdAndSort(Long skillId, Sort sort) {
        return sessionRepository.findBySkillId(skillId, sort);
    }

    @Transactional(readOnly = true)
    public List<LearningSession> findBySkillId(Long skillId) {
        return sessionRepository.findBySkillId(skillId);
    }

    @Transactional(readOnly = true)
    public List<LearningSession> findBySkillAndDateRange(
            Long skillId, LocalDate start, LocalDate end) {
        return sessionRepository.findBySkillIdAndSessionDateBetween(skillId, start, end);
    }

    @Transactional(readOnly = true)
    public Integer getTotalMinutesForSkill(Long skillId) {
        return sessionRepository.getTotalMinutesBySkillId(skillId);
    }

    @Transactional(readOnly = true)
    public Map<String, Integer> getTotalMinutesGroupedBySkillForUser(Long userId) {
        List<Object[]> results = sessionRepository.getTotalMinutesGroupedBySkillForUser(userId);
        Map<String, Integer> resultMap = new HashMap<>();
        for (Object[] row : results) {
            resultMap.put((String) row[0], ((Number) row[1]).intValue());
        }
        return resultMap;
    }
}