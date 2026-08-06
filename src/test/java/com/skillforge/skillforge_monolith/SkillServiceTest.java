package com.skillforge.skillforge_monolith;

import com.skillforge.skillforge_monolith.entity.Skill;
import com.skillforge.skillforge_monolith.entity.User;
import com.skillforge.skillforge_monolith.repository.SkillRepository;
import com.skillforge.skillforge_monolith.repository.UserRepository;
import com.skillforge.skillforge_monolith.service.SkillService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {
    @Mock
    private SkillRepository skillRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private SkillService skillService;

    @Test
    void createSkill_ShouldReturnSavedSkill_WhenUserExists() {
        User user = User.builder().id(1L).email("test@test.com").build();
        Skill skill = Skill.builder().name("Java").category("Programming").user(user).build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Correct Mockito syntax using the right any() matcher
        when(skillRepository.save(any(Skill.class))).thenReturn(skill);

        Skill result = skillService.createSkill(1L, "Java", "Programming", "Learn Java");

        assertThat(result.getName()).isEqualTo("Java");
    }

    @Test
    void createSkill_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> skillService.createSkill(0L, "Java", "Prog", ""))
                .isInstanceOf(EntityNotFoundException.class);
    }
}