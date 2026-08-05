package com.skillforge.skillforge_monolith.service;

import com.skillforge.skillforge_monolith.entity.Skill;
import com.skillforge.skillforge_monolith.entity.User;
import com.skillforge.skillforge_monolith.repository.SkillRepository;
import com.skillforge.skillforge_monolith.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SkillService {
    private SkillRepository skillRepository;
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<Skill> findById(String id) {
        return skillRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Skill> findByIdWithSessions(String id) {
        return skillRepository.findByIdWithSessions(id);
    }

    @Transactional(readOnly = true)
    public List<Skill> findByUserId(String userId) {
        return skillRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Page<Skill> findByUserId(String userId, Pageable pageable) {
        return skillRepository.findByUserId(userId, pageable);
    }

    public Skill createSkill(String userId, String name, String category, String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        Skill skill = Skill.builder()
                .name(name)
                .category(category)
                .description(description)
                .user(user)
                .build();
        user.addSkill(skill);  // Bidirectional sync
        return skillRepository.save(skill);
    }

    public Skill updateSkill(String skillId, String name, String category, String description) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityNotFoundException("Skill not found: " + skillId));
        skill.setName(name);
        skill.setCategory(category);
        skill.setDescription(description);
        return skillRepository.save(skill);
    }

    public void deleteSkill(String skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityNotFoundException("Skill not found: " + skillId));
        User user = skill.getUser();
        user.removeSkill(skill);  // Bidirectional sync
        skillRepository.delete(skill);
    }

    @Transactional(readOnly = true)
    public long countByUserId(String userId) {
        return skillRepository.countByUserId(userId);
    }
}
