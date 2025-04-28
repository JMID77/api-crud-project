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
    
    // @PersistenceContext
    // EntityManager entityManager; // donné par Spring pour dialoguer avec la base
    
    public JpaActionRepositoryImpl(@Lazy JpaActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Override
    public Action save(Action action) {
        return this.actionRepository.save(action);
    }

    @Override
    public Optional<Action> findById(Long id) {
        return this.actionRepository.findById(id);
    }

    @Override
    public List<Action> findAll() {
        return actionRepository.findAll();


        // Code pour => @PersistenceContext
        // return entityManager.createQuery("FROM Action", Action.class)
        //                      .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        this.actionRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return this.actionRepository.existsById(id);
    }

    @Override
    public List<Action> findActionStatusByStatus(String actionStatut) {
        return this.actionRepository.findActionStatusByStatus(actionStatut);
    }
}