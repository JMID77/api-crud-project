package com.api.crud.apiCrudProject.application.dto;

import com.api.crud.apiCrudProject.domain.entity.Language;
import com.api.crud.apiCrudProject.domain.entity.RoleType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserSystemRequest(
    @NotBlank(message = "{userSysName.notblank.message}")
    @Size(max=50, message = "{userSysName.size.message}")
    String username,
    
    @NotBlank(message = "{userSysPassword.notblank.message}")
    @Size(max=30, message = "{userSysPassword.size.message}")
    String password,

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{userSysLanguage.notnull.message}")
    Language language,

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{userSysRole.notnull.message}")
    RoleType role

) {}
