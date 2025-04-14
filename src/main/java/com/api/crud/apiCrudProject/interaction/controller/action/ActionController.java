package com.api.crud.apiCrudProject.interaction.controller.action;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.crud.apiCrudProject.application.dto.actions.ActionResponse;
import com.api.crud.apiCrudProject.application.dto.actions.ActionRequest;
import com.api.crud.apiCrudProject.application.service.actions.ActionsService;
import com.api.crud.apiCrudProject.domain.entity.actions.ActionStatus;
import com.api.crud.apiCrudProject.infrastructure.exception.actions.ActionNotFoundException;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/actions")
public class ActionController {
    private final ActionsService actionService;

    public ActionController(ActionsService actionService) {
        this.actionService = actionService;
    }

    @PostMapping
    public ActionResponse createAction(@RequestBody @Valid ActionRequest request) {
        return actionService.createAction(request);
    }

    @GetMapping("/{id}")
    public EntityModel<ActionResponse> getActionById(@PathVariable Long id) {
    //public ActionResponse getActionById(@PathVariable Long id) {
        ActionResponse action = actionService.getAction(id)
                                    .orElseThrow(() -> new ActionNotFoundException("Action with ID " + id + " not found", id));
        
        EntityModel<ActionResponse> actionModel = EntityModel.of(action);
         // Lien vers soi-même
        actionModel.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ActionController.class).getActionById(id)
            ).withSelfRel()
        );

        // Exemple de lien vers la suppression de l’action
        actionModel.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ActionController.class).deleteActionById(id)
            ).withRel("delete")
        );

        // Exemple d’un autre lien (modification, liste globale, etc.)
        actionModel.add(
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ActionController.class).getAllActions()
            ).withRel("all-actions")
        );
        
        return actionModel;
        /*
        return actionService.getAction(id)
                .orElseThrow(() -> new ActionNotFoundException("Action with ID " + id + " not found", id));
        */
    }

    @GetMapping
    public List<ActionResponse> getAllActions() {
        return actionService.getAllActions();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActionById(@PathVariable Long id) {
        actionService.deleteActionById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/statuses")
    public ActionStatus[] getAllStatuses() {
        return ActionStatus.values();
    }
}
