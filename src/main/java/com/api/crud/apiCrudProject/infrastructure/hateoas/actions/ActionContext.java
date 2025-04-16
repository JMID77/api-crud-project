package com.api.crud.apiCrudProject.infrastructure.hateoas.actions;

public enum ActionContext {
    DEFAULT,       // Utilisé dans les requêtes classiques
    AFTER_CREATE,  // Après la création de l'action
    AFTER_UPDATE,  // Après la mise à jour
    AFTER_DELETE   // Après la suppression
}
