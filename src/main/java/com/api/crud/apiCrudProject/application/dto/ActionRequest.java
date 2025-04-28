package com.api.crud.apiCrudProject.application.dto;

import com.api.crud.apiCrudProject.domain.entity.ActionStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ActionRequest(
    @NotBlank(message = "{actionName.notblank.message}")
    @Size(max=100, message = "{actionName.size.message}")
    String actionName,

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{actionStatus.notnull.message}")
    ActionStatus actionStatus
) {}
