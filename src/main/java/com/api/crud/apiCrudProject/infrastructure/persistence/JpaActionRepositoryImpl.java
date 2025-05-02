package com.api.crud.apiCrudProject.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.api.crud.apiCrudProject.domain.entity.Action;
import com.api.crud.apiCrudProject.domain.repository.ActionRepository;


@Repository
@Qualifier("actionSpecificRepo")
public class JpaActionRepositoryImpl implements ActionRepository {
    private JpaActionRepository actionRepository;
    
    
    public JpaActionRepositoryImpl(@Lazy JpaActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Override
    public Action persist(Action action) {
        return this.actionRepository.save(action);
    }

    @Override
    public Optional<Action> searchById(Long id) {
        return this.actionRepository.findById(id);
    }

    @Override
    public List<Action> searchAll() {
        return actionRepository.findAll();
    }

    @Override
    public void removeById(Long id) {
        this.actionRepository.deleteById(id);
    }

    @Override
    public boolean checkById(Long id) {
        return this.actionRepository.existsById(id);
    }

    @Override
    public List<Action> searchActionStatusByStatus(String actionStatut) {
        return this.actionRepository.findActionStatusByStatus(actionStatut);
    }
}