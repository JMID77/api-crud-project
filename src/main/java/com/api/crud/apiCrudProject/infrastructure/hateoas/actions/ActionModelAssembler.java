package com.api.crud.apiCrudProject.infrastructure.hateoas.actions;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.api.crud.apiCrudProject.application.dto.actions.ActionResponse;
import com.api.crud.apiCrudProject.domain.entity.actions.ActionStatus;
import com.api.crud.apiCrudProject.interaction.controller.action.ActionController;

@Component
public class ActionModelAssembler {

    // Méthode principale pour assembler les liens HATEOAS en fonction du contexte
    public EntityModel<ActionResponse> toModel(ActionResponse action, ActionContext context) {
        EntityModel<ActionResponse> model = EntityModel.of(action);

        // Lien vers soi-même (toujours présent)
        model.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(ActionController.class).getActionById(action.id())
                ).withSelfRel()
                .withType(HttpMethod.GET.name()));

        // Ajouter un lien de suppression, sauf si le contexte est "DELETE"
        if (context != ActionContext.AFTER_DELETE) {
            model.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ActionController.class).deleteActionById(action.id())
                    ).withRel("delete")
                    .withType(HttpMethod.DELETE.name()));
        }

        // Ajouter un lien de mise à jour, sauf si le statut de l'action est "CLOSED"/"CANCELED" ou le contexte est "AFTER_UPDATE"
        if (context != ActionContext.AFTER_UPDATE && (action.actionStatus() != ActionStatus.COMPLETED && action.actionStatus() != ActionStatus.CANCELLED)) {
            model.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ActionController.class).updateAction(action.id(), null)  // null ici, à adapter
                    ).withRel("update")
                    .withType(HttpMethod.PUT.name()));
        }

        // Ajouter un lien pour créer une nouvelle action, sauf si déjà après création
        if (context != ActionContext.AFTER_CREATE) {
            model.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ActionController.class).createAction(null)  // null ici, à adapter
                    ).withRel("create")
                    .withType(HttpMethod.POST.name()));
        }

        // Autres liens que tu souhaites ajouter
        model.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(ActionController.class).getAllActions()
                ).withRel("all-actions")
                .withType(HttpMethod.GET.name()));

        return model;
    }
}
