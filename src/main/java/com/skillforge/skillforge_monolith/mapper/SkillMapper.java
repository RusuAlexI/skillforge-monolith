package com.skillforge.skillforge_monolith.mapper;

import com.skillforge.skillforge_monolith.dto.response.SkillResponse;
import com.skillforge.skillforge_monolith.entity.LearningSession;
import com.skillforge.skillforge_monolith.entity.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    @Mapping(target = "totalMinutes", expression = "java(calculateTotalMinutes(skill))")
    SkillResponse toResponse(Skill skill);

    List<SkillResponse> toResponseList(List<Skill> skills);

    default Integer calculateTotalMinutes(Skill skill) {
        if (skill == null || skill.getSessions() == null) {
            return 0;
        }
        return skill.getSessions().stream()
                .mapToInt(LearningSession::getDurationMinutes)
                .sum();
    }
}
