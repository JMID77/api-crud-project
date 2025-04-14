package com.api.crud.apiCrudProject.application.dto.actions;

import com.api.crud.apiCrudProject.domain.entity.actions.ActionStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record ActionResponse(Long id, String actionName, @Enumerated(EnumType.STRING) ActionStatus actionStatus) {

}
