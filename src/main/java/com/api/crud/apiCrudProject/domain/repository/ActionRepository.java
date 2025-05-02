package com.api.crud.apiCrudProject.domain.repository;

import java.util.List;
import java.util.Optional;

import com.api.crud.apiCrudProject.domain.entity.Action;

public interface ActionRepository {
    Action persist(Action action);
    Optional<Action> searchById(Long id);
    List<Action> searchAll();
    void removeById(Long id);
    boolean checkById(Long id);

    List<Action> searchActionStatusByStatus(String actionStatus);
}
