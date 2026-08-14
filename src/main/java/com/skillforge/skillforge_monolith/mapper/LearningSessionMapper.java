package com.skillforge.skillforge_monolith.mapper;

import com.skillforge.skillforge_monolith.dto.response.LearningSessionResponse;
import com.skillforge.skillforge_monolith.entity.LearningSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface LearningSessionMapper {

    @Mapping(source = "skill.id", target = "skillId")
    @Mapping(source = "skill.name", target = "skillName")
    LearningSessionResponse toResponse(LearningSession session);

    default List<LearningSessionResponse> toResponseList(List<LearningSession> sessions) {
        if (sessions == null) return List.of();
        return sessions.stream()
                .filter(s -> s != null && s.getSkill() != null)
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}