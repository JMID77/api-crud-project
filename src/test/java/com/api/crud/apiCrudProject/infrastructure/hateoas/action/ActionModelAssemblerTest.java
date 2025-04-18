package com.api.crud.apiCrudProject.infrastructure.hateoas.action;

import com.api.crud.apiCrudProject.application.dto.actions.ActionResponse;
import com.api.crud.apiCrudProject.domain.entity.actions.ActionStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ActionModelAssemblerTest {

    private ActionModelAssembler assembler;

    @BeforeEach
    void setup() {
        assembler = new ActionModelAssembler();
    }

    @Test
    void whenContextIsDefault_thenAllLinksPresent() {
        ActionResponse action = new ActionResponse(1L, "Test", ActionStatus.IN_PROGRESS);
        EntityModel<ActionResponse> model = assembler.toModel(action, ActionContext.DEFAULT);

        List<String> links = model.getLinks().stream().map(link -> link.getRel().value()).collect(Collectors.toList());

        assertTrue(links.contains("self"));
        assertTrue(links.contains("delete"));
        assertTrue(links.contains("update"));
        assertTrue(links.contains("create"));
        assertTrue(links.contains("all-actions"));
    }

    @Test
    void whenContextIsAfterCreate_thenNoCreateLink() {
        ActionResponse action = new ActionResponse(2L, "Nouvelle action", ActionStatus.CREATED);
        EntityModel<ActionResponse> model = assembler.toModel(action, ActionContext.AFTER_CREATE);

        assertFalse(model.hasLink("create"));
    }

    @Test
    void whenContextIsAfterUpdate_thenNoUpdateLinkEvenIfStatusIsCreated() {
        ActionResponse action = new ActionResponse(3L, "Mise à jour", ActionStatus.CREATED);
        EntityModel<ActionResponse> model = assembler.toModel(action, ActionContext.AFTER_UPDATE);

        assertFalse(model.hasLink("update"));
    }

    @Test
    void whenContextIsAfterDelete_thenNoDeleteLink() {
        ActionResponse action = new ActionResponse(4L, "Supprimée", ActionStatus.CANCELLED);
        EntityModel<ActionResponse> model = assembler.toModel(action, ActionContext.AFTER_DELETE);

        assertFalse(model.hasLink("delete"));
    }

    @Test
    void whenStatusIsCompleted_thenNoUpdateLink() {
        ActionResponse action = new ActionResponse(5L, "Terminée", ActionStatus.COMPLETED);
        EntityModel<ActionResponse> model = assembler.toModel(action, ActionContext.DEFAULT);

        assertFalse(model.hasLink("update"));
    }

    @Test
    void whenStatusIsCancelled_thenNoUpdateLink() {
        ActionResponse action = new ActionResponse(6L, "Annulée", ActionStatus.CANCELLED);
        EntityModel<ActionResponse> model = assembler.toModel(action, ActionContext.DEFAULT);

        assertFalse(model.hasLink("update"));
    }
}
