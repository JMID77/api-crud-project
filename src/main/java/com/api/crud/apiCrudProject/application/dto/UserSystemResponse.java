package com.api.crud.apiCrudProject.application.dto;

import com.api.crud.apiCrudProject.domain.entity.enumeration.Language;
import com.api.crud.apiCrudProject.domain.entity.enumeration.RoleType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


public record UserSystemResponse(Long id, String username, String password, @Enumerated(EnumType.STRING) Language language, @Enumerated(EnumType.STRING) RoleType role) {}
