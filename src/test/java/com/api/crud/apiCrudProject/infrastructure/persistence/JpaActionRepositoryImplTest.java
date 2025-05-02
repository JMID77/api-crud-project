package com.api.crud.apiCrudProject.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.api.crud.apiCrudProject.domain.entity.Action;
import com.api.crud.apiCrudProject.domain.entity.enumeration.ActionStatus;
import com.api.crud.apiCrudProject.domain.repository.ActionRepository;

@SpringBootTest
class JpaActionRepositoryImplTest {
    
    @Autowired
    @Qualifier("actionSpecificRepo")
    private ActionRepository actionRepository;
    @Autowired
    @Qualifier("actionJpaRepo")
    private JpaActionRepository actionRepo;


    // @TestConfiguration
    // static class TestConfigRepository {
    //     @Bean
    //     @Primary
    //     public JpaActionRepository actionRepository(JpaActionRepository jpaActionRepository) {
    //         return jpaActionRepository;
    //     }
    // }

    @Test
    void userSysRepository_shouldBeInstanceOfJpaActionRepositoryImpl() {
        assertNotNull(actionRepository);
        assertTrue(actionRepository instanceof JpaActionRepositoryImpl,
                        "actionRepository should be an instance of JpaActionRepositoryImpl");
        
        assertNotNull(actionRepo);
        assertInstanceOf(JpaActionRepository.class, actionRepo,
                        "actionRepo should be an instance of JpaActionRepository");
    }

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

    // @Test
    // void findAll_shouldReturnAllActions() {
    //     Action a1 = Action.builder().actionName("A1").actionStatus(ActionStatus.CREATED).build();
    //     Action a2 = Action.builder().actionName("A2").actionStatus(ActionStatus.COMPLETED).build();

    //     actionRepo.save(a1);
    //     actionRepo.save(a2);

    //     List<Action> all = actionRepo.findAll();
    //     // List<Action> all = new ArrayList<Action>();
    //     // actionRepository.findAll().forEach(action -> all.add(action));
    //     assertEquals(2, all.size());
    // }

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
