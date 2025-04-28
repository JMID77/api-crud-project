package com.api.crud.apiCrudProject.application.mapper;

import org.springframework.stereotype.Component;

import com.api.crud.apiCrudProject.application.dto.UserSystemRequest;
import com.api.crud.apiCrudProject.application.dto.UserSystemResponse;
import com.api.crud.apiCrudProject.domain.entity.UserSystem;

@Component
public class UserSystemMapper {

    public UserSystem toEntity(UserSystemRequest user) {
        return new UserSystem(null, user.username(), user.password(), 
                                        user.language(), user.role());
    }

    public UserSystemResponse toResponse(UserSystem user) {
        return new UserSystemResponse(user.getId(), user.getUsername(), user.getPassword(), 
                                                user.getLanguage(), user.getRole());
    }
}
