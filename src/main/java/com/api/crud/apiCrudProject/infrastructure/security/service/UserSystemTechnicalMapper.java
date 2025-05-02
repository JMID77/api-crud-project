package com.api.crud.apiCrudProject.infrastructure.security.service;

import com.api.crud.apiCrudProject.domain.entity.enumeration.RoleType;
import com.api.crud.apiCrudProject.domain.entity.UserSystem;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserSystemTechnicalMapper {
    public UserDetails toUserDetail(UserSystem user) {
        UserDetails userDet = User.withUsername(user.getUsername())
                                .password(user.getPassword())
                                .roles(roleTypeToString(user.getRole()))
                                .build();
        return userDet;
    }

    public String roleTypeToString(RoleType role) {
        return (role.name().replace("ROLE_", ""));
    }
}
