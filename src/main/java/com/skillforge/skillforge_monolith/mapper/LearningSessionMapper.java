package com.skillforge.skillforge_monolith.mapper;

import com.skillforge.skillforge_monolith.dto.response.LearningSessionResponse;
import com.skillforge.skillforge_monolith.entity.LearningSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LearningSessionMapper {
    @Mapping(source = "skill.id", target = "skillId")
    @Mapping(source = "skill.name", target = "skillName")
    LearningSessionResponse toResponse(LearningSession session);

    List<LearningSessionResponse> toResponseList(List<LearningSession> sessions);
}
