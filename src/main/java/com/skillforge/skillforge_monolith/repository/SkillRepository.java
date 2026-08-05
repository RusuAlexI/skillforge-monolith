package com.skillforge.skillforge_monolith.repository;

import com.skillforge.skillforge_monolith.entity.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, String> {

    List<Skill> findByUserId(String userId);

    List<Skill> findByUserIdAndCategory(String userId, String category);

    Page<Skill> findByUserId(String userId, Pageable pageable);

    @Query("SELECT s FROM Skill s JOIN FETCH s.sessions WHERE s.id = :id")
    Optional<Skill> findByIdWithSessions(@Param("id") String id);

    @Query("SELECT COUNT(s) FROM Skill s WHERE s.user.id = :userId")
    long countByUserId(@Param("userId") String userId);

}
