package com.skillforge.tracking.repository;

import com.skillforge.tracking.entity.LearningSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LearningSessionRepository extends JpaRepository<LearningSession, Long> {

    List<LearningSession> findBySkillId(Long skillId);

    @Query("SELECT COALESCE(SUM(ls.durationMinutes), 0) FROM LearningSession ls WHERE ls.skillId = :skillId")
    Integer getTotalMinutesBySkillId(@Param("skillId") Long skillId);

    void deleteBySkillId(Long skillId);
}