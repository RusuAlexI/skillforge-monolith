package com.skillforge.skill.service;

import com.skillforge.skill.entity.Skill;
import com.skillforge.skill.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public List<Skill> findByUserId(Long userId) {
        return skillRepository.findByUserId(userId);
    }

    public Page<Skill> findByUserId(Long userId, Pageable pageable) {
        return skillRepository.findByUserId(userId, pageable);
    }

    public Skill findById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Skill not found: " + id));
    }

    public Skill createSkill(Long userId, String name, String category, String description) {
        Skill skill = Skill.builder()
                .name(name)
                .category(category)
                .description(description)
                .userId(userId)
                .build();
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Long id, String name, String category, String description) {
        Skill skill = findById(id);
        skill.setName(name);
        skill.setCategory(category);
        skill.setDescription(description);
        return skillRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }
}