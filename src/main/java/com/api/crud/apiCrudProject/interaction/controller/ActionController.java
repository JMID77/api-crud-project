package com.api.crud.apiCrudProject.interaction.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.crud.apiCrudProject.application.dto.ActionRequest;
import com.api.crud.apiCrudProject.application.dto.ActionResponse;
import com.api.crud.apiCrudProject.application.service.ActionsService;
import com.api.crud.apiCrudProject.domain.entity.enumeration.ActionStatus;
import com.api.crud.apiCrudProject.infrastructure.hateoas.ActionContext;
import com.api.crud.apiCrudProject.infrastructure.hateoas.ActionModelAssembler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vES.1/actions")   // Version vES.1 => version external service 1
public class ActionController {
    private final ActionsService actionService;
    private final ActionModelAssembler actionModelAssembler;

    public ActionController(ActionsService actionService, ActionModelAssembler actionModelAssembler) {
        this.actionService = actionService;
        this.actionModelAssembler = actionModelAssembler;
    }

    @GetMapping("/secured")
    public ResponseEntity<String> securedEndpoint(Authentication auth) {
        System.out.println("User = " + auth.getName());
        System.out.println("Authorities = " + auth.getAuthorities());
        return ResponseEntity.ok("OK");
    }

    @PostMapping
    public ResponseEntity<EntityModel<ActionResponse>> createAction(@RequestBody @Valid ActionRequest request) {
        ActionResponse createdAction = this.actionService.createAction(request);
        
        EntityModel<ActionResponse> actionModel = this.actionModelAssembler.toModel(createdAction, ActionContext.AFTER_CREATE);
        
        return ResponseEntity
                .created(WebMvcLinkBuilder.linkTo(
                            WebMvcLinkBuilder.methodOn(ActionController.class).getActionById(createdAction.id())
                        ).toUri()
                    ).body(actionModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ActionResponse>> updateAction(@PathVariable Long id, @RequestBody @Valid ActionRequest request) {
        ActionResponse updatedAction = this.actionService.updateAction(id, request);
        
        EntityModel<ActionResponse> actionModel = this.actionModelAssembler.toModel(updatedAction, ActionContext.AFTER_UPDATE);
        
        return ResponseEntity.ok(actionModel);
    }

    @GetMapping("/{id}")
    public EntityModel<ActionResponse> getActionById(@PathVariable Long id) {
        return this.actionModelAssembler.toModel(this.actionService.getAction(id), ActionContext.DEFAULT);
    }

    @GetMapping
    public List<EntityModel<ActionResponse>> getAllActions() {
        return actionService.getAllActions()
                            .stream()
                            .map(action -> this.actionModelAssembler.toModel(action, ActionContext.DEFAULT))
                            .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActionById(@PathVariable Long id) {
        this.actionService.deleteActionById(id);
        // Return a 204, if the record doesn't exists, the service throws an exception RessourceNotFound
        return ResponseEntity.noContent().build();
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
