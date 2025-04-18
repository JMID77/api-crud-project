package com.api.crud.apiCrudProject.infrastructure.persistence.action;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.api.crud.apiCrudProject.domain.entity.actions.Action;
import com.api.crud.apiCrudProject.domain.entity.actions.ActionStatus;
import com.api.crud.apiCrudProject.infrastructure.persistence.actions.JpaActionRepository;

@DataJpaTest
class JpaActionRepositoryTest {

    @Autowired
    private JpaActionRepository actionRepository;

    @Test
    void save_shouldPersistAction() {
        Action action = Action.builder()
                .actionName("Test Persistence")
                .actionStatus(ActionStatus.CREATED)
                .build();

        Action saved = actionRepository.save(action);

        assertNotNull(saved.getId(), "ID should be generated");
        assertEquals("Test Persistence", saved.getActionName());
        assertEquals(ActionStatus.CREATED, saved.getActionStatus());
    }

    @Test
    void findById_shouldReturnSavedAction() {
        Action action = Action.builder()
                .actionName("Find Me")
                .actionStatus(ActionStatus.IN_PROGRESS)
                .build();

        Action saved = actionRepository.save(action);
        Optional<Action> found = actionRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Find Me", found.get().getActionName());
    }

    @Test
    void findAll_shouldReturnAllActions() {
        Action a1 = Action.builder().actionName("A1").actionStatus(ActionStatus.CREATED).build();
        Action a2 = Action.builder().actionName("A2").actionStatus(ActionStatus.COMPLETED).build();

        actionRepository.save(a1);
        actionRepository.save(a2);

        List<Action> all = actionRepository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void existsById_shouldReturnTrue() {
        Action action = Action.builder()
                .actionName("Existing")
                .actionStatus(ActionStatus.CANCELLED)
                .build();

        Action saved = actionRepository.save(action);
        assertTrue(actionRepository.existsById(saved.getId()));
    }

    @Test
    void deleteById_shouldRemoveAction() {
        Action action = Action.builder()
                .actionName("Delete Me")
                .actionStatus(ActionStatus.CREATED)
                .build();

        Action saved = actionRepository.save(action);
        Long id = saved.getId();

        actionRepository.deleteById(id);
        assertFalse(actionRepository.findById(id).isPresent());
    }
}
