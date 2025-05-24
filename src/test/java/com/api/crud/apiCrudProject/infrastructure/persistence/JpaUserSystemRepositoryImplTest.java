package com.api.crud.apiCrudProject.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.crud.apiCrudProject.domain.entity.UserSystem;
import com.api.crud.apiCrudProject.domain.entity.enumeration.Language;
import com.api.crud.apiCrudProject.domain.entity.enumeration.RoleType;

public class JpaUserSystemRepositoryImplTest {

    private JpaUserSystemRepository jpaRepo;
    private JpaUserSystemRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        jpaRepo = mock(JpaUserSystemRepository.class);
        repository = new JpaUserSystemRepositoryImpl(jpaRepo);
    }

    @Test
    void persist_shouldCallSaveAndReturnEntity() {
        UserSystem user = new UserSystem(null, "john", "secret", Language.ENGLISH, RoleType.ROLE_USER);
        UserSystem savedUser = new UserSystem(1L, "john", "secret", Language.ENGLISH, RoleType.ROLE_USER);

        when(jpaRepo.save(user)).thenReturn(savedUser);

        UserSystem result = repository.persist(user);

        assertEquals(savedUser, result);
        verify(jpaRepo).save(user);
    }

    @Test
    void searchById_shouldReturnUserSystem_whenExists() {
        Long id = 1L;
        UserSystem user = new UserSystem(id, "john", "secret", Language.ENGLISH, RoleType.ROLE_USER);

        when(jpaRepo.findById(id)).thenReturn(Optional.of(user));

        Optional<UserSystem> result = repository.searchById(id);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(jpaRepo).findById(id);
    }

    @Test
    void searchById_shouldReturnEmpty_whenNotFound() {
        Long id = 2L;

        when(jpaRepo.findById(id)).thenReturn(Optional.empty());

        Optional<UserSystem> result = repository.searchById(id);

        assertFalse(result.isPresent());
        verify(jpaRepo).findById(id);
    }

    @Test
    void searchAll_shouldReturnListOfUsers() {
        List<UserSystem> users = List.of(
            new UserSystem(1L, "john", "pass", Language.FRENCH, RoleType.ROLE_USER),
            new UserSystem(2L, "jane", "pass", Language.ENGLISH, RoleType.ROLE_ADMIN)
        );

        when(jpaRepo.findAll()).thenReturn(users);

        List<UserSystem> result = repository.searchAll();

        assertEquals(users, result);
        verify(jpaRepo).findAll();
    }

    @Test
    void removeById_shouldCallDeleteById() {
        Long id = 1L;

        repository.removeById(id);

        verify(jpaRepo).deleteById(id);
    }

    @Test
    void checkById_shouldReturnTrue_whenExists() {
        Long id = 1L;

        when(jpaRepo.existsById(id)).thenReturn(true);

        boolean result = repository.checkById(id);

        assertTrue(result);
        verify(jpaRepo).existsById(id);
    }

    @Test
    void checkById_shouldReturnFalse_whenNotExists() {
        Long id = 99L;

        when(jpaRepo.existsById(id)).thenReturn(false);

        boolean result = repository.checkById(id);

        assertFalse(result);
        verify(jpaRepo).existsById(id);
    }

    @Test
    void searchByUsername_shouldReturnUser_whenExists() {
        String username = "john";
        UserSystem user = new UserSystem(1L, "john", "secret", Language.ENGLISH, RoleType.ROLE_USER);

        when(jpaRepo.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<UserSystem> result = repository.searchByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(jpaRepo).findByUsername(username);
    }

    @Test
    void searchByUsername_shouldReturnEmpty_whenNotFound() {
        String username = "unknown";

        when(jpaRepo.findByUsername(username)).thenReturn(Optional.empty());

        Optional<UserSystem> result = repository.searchByUsername(username);

        assertFalse(result.isPresent());
        verify(jpaRepo).findByUsername(username);
    }
}
