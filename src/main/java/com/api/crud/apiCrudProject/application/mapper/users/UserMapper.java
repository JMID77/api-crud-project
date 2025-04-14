package com.api.crud.apiCrudProject.application.mapper.users;

import org.springframework.stereotype.Component;

import com.api.crud.apiCrudProject.application.dto.users.UserRequest;
import com.api.crud.apiCrudProject.application.dto.users.UserResponse;
import com.api.crud.apiCrudProject.domain.entity.users.User;

@Component
public class UserMapper {
    public User toEntity(UserRequest userRequest) {
        return new User(null, userRequest.userName(), userRequest.email(), userRequest.password());
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getUserName(), user.getEmail());
    }
}
