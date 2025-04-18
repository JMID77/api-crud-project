package com.api.crud.apiCrudProject.application.mapper.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.crud.apiCrudProject.application.dto.actions.ActionRequest;
import com.api.crud.apiCrudProject.application.dto.actions.ActionResponse;
import com.api.crud.apiCrudProject.application.mapper.actions.ActionMapper;
import com.api.crud.apiCrudProject.domain.entity.actions.Action;
import com.api.crud.apiCrudProject.domain.entity.actions.ActionStatus;

public class ActionMapperTest {
    private ActionMapper actionMapper;

    @BeforeEach
    public void setUp() {
        this.actionMapper = new ActionMapper();
    }

    @Test
    public void toEntity_shouldMapCorrectly() {
        // Arrange
        ActionRequest request = new ActionRequest("Faire les courses", ActionStatus.IN_PROGRESS);

        // Act
        Action action = this.actionMapper.toEntity(request);

        // Assert
        assertNull(action.getId()); // Le mapper met toujours l’id à null
        assertEquals("Faire les courses", action.getActionName());
        assertEquals(ActionStatus.IN_PROGRESS, action.getActionStatus());
    }

    @Test
     public void toResponse_shouldMapCorrectly() {
        // Arrange
        Action action = new Action(42L, "Lire un livre", ActionStatus.COMPLETED);

        // Act
        ActionResponse response = this.actionMapper.toResponse(action);

        // Assert
        assertEquals(42L, response.id());
        assertEquals("Lire un livre", response.actionName());
        assertEquals(ActionStatus.COMPLETED, response.actionStatus());
    }

}
