package com.skillforge.skillforge_monolith.repository;

import com.skillforge.skillforge_monolith.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, String> {
}
