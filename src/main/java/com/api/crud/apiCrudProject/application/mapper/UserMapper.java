package com.api.crud.apiCrudProject.application.mapper;

import org.springframework.stereotype.Component;

import com.api.crud.apiCrudProject.application.dto.UserRequest;
import com.api.crud.apiCrudProject.application.dto.UserResponse;
import com.api.crud.apiCrudProject.domain.entity.User;

@Component
public class UserMapper {
    public User toEntity(UserRequest userRequest) {
        return new User(null, userRequest.username(), userRequest.email(), userRequest.password());
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getUserName(), user.getEmail());
    }
}
