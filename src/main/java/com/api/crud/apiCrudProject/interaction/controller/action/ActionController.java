package com.api.crud.apiCrudProject.interaction.controller.action;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.crud.apiCrudProject.application.dto.actions.ActionResponse;
import com.api.crud.apiCrudProject.application.dto.actions.ActionRequest;
import com.api.crud.apiCrudProject.application.service.actions.ActionsService;
import com.api.crud.apiCrudProject.domain.entity.actions.ActionStatus;
import com.api.crud.apiCrudProject.infrastructure.exception.actions.ActionNotFoundException;
import com.api.crud.apiCrudProject.infrastructure.hateoas.action.ActionContext;
import com.api.crud.apiCrudProject.infrastructure.hateoas.action.ActionModelAssembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/actions")
public class ActionController {
    private final ActionsService actionService;
    private final ActionModelAssembler actionModelAssembler;

    public ActionController(ActionsService actionService, ActionModelAssembler actionModelAssembler) {
        this.actionService = actionService;
        this.actionModelAssembler = actionModelAssembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<ActionResponse>> createAction(@RequestBody @Valid ActionRequest request) {
        ActionResponse createdAction = this.actionService.createAction(request);
        
        EntityModel<ActionResponse> actionModel = this.actionModelAssembler.toModel(createdAction, ActionContext.AFTER_CREATE);
        
        return ResponseEntity
                .created(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(ActionController.class).getActionById(createdAction.id())
                ).toUri())
                .body(actionModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ActionResponse>> updateAction(@PathVariable Long id, @RequestBody @Valid ActionRequest request) {
        ActionResponse updatedAction = this.actionService.updateAction(id, request);
        
        EntityModel<ActionResponse> actionModel = this.actionModelAssembler.toModel(updatedAction, ActionContext.AFTER_UPDATE);
        
        return ResponseEntity.ok(actionModel);
    }

    @GetMapping("/{id}")
    public EntityModel<ActionResponse> getActionById(@PathVariable Long id) {
        ActionResponse action = actionService.getAction(id)
                                    .orElseThrow(() -> new ActionNotFoundException("Action with ID " + id + " not found", id));
        
        return this.actionModelAssembler.toModel(action, ActionContext.DEFAULT);
    }

    @GetMapping
    public List<EntityModel<ActionResponse>> getAllActions() {
        return actionService.getAllActions().stream()
                            .map(action -> this.actionModelAssembler.toModel(action, ActionContext.DEFAULT))
                            .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActionById(@PathVariable Long id) {
        boolean deleted = actionService.deleteActionById(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/statuses")
    public ActionStatus[] getAllStatuses() {
        return ActionStatus.values();
    }

    @GetMapping("/status/{status}")
    public List<EntityModel<ActionResponse>> getActionsByStatus(@PathVariable ActionStatus status) {
        return actionService.getActionsByStatus(status).stream()
                            .map(action -> this.actionModelAssembler.toModel(action, ActionContext.DEFAULT))
                            .collect(Collectors.toList());
    }
}
