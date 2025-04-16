package com.api.crud.apiCrudProject.domain.repository.actions;

import java.util.List;
import java.util.Optional;

import com.api.crud.apiCrudProject.domain.entity.actions.Action;

public interface ActionRepository {
    Action save(Action action);
    Optional<Action> findById(Long id);
    List<Action> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
