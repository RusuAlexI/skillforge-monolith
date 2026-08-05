package com.skillforge.skillforge_monolith.repository;

import com.skillforge.skillforge_monolith.entity.LearningSession;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LearningSessionRepository extends JpaRepository<LearningSession, String> {

    List<LearningSession> findBySkillId(String skillId, Sort sort);

    List<LearningSession> findBySkillIdAndSessionDateBetween(String skillId, LocalDate start, LocalDate end);

    @Query("SELECT COALESCE(SUM(ls.durationMinutes), 0) FROM LearningSession ls WHERE ls.skill.id = :skillId")
    Integer getTotalMinutesBySkillId(@Param("skillId") String skillId);

    @Query("SELECT ls.skill.id, COALESCE(SUM(ls.durationMinutes), 0) " +
            "FROM LearningSession ls WHERE ls.skill.user.id = :userId GROUP BY ls.skill.id")
    List<Object[]> getTotalMinutesGroupedBySkillForUser(@Param("userId") String userId);

    void deleteBySkillId(String skillId);

}
