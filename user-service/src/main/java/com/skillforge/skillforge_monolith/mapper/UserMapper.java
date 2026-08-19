package com.skillforge.skillforge_monolith.mapper;

import com.skillforge.skillforge_monolith.dto.response.UserResponse;
import com.skillforge.skillforge_monolith.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);
    List<UserResponse> toResponseList(List<User> users);
}