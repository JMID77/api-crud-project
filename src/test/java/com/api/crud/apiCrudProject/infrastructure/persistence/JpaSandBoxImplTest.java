package com.api.crud.apiCrudProject.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.api.crud.apiCrudProject.domain.entity.Sandbox;
import com.api.crud.apiCrudProject.domain.repository.SandboxRepository;

@SpringBootTest
public class JpaSandBoxImplTest {
    
    @Autowired
    @Qualifier("sandboxSpecificRepo")
    private SandboxRepository sandboxRepository;
    @Autowired
    @Qualifier("sandboxJpaRepo")
    private JpaSandboxRepository sandboxRepo;

    
    @Test
    void userSysRepository_shouldBeInstanceOfJpaSandBoxRepositoryImpl() {
        assertNotNull(sandboxRepository);
        assertTrue(sandboxRepository instanceof JpaSandBoxRepositoryImpl,
                        "sandboxRepository should be an instance of JpaSandBoxRepositoryImpl");
        // assertEquals(JpaUserSystemRepositoryImpl.class, userSysRepository.getClass(), 
        //     "userSysRepository should be an instance of JpaUserSystemRepositoryImpl");

        assertNotNull(sandboxRepo);
        assertInstanceOf(JpaSandboxRepository.class, sandboxRepo,
                        "sandboxRepo should be an instance of JpaSandboxRepository");
    }

    @Test
    void testFindAll() {
        // Given
        Sandbox sandbox1 = new Sandbox(null, "Sandbox 1");
        Sandbox sandbox2 = new Sandbox(null, "Sandbox 2");
        sandboxRepository.persist(sandbox1);
        sandboxRepository.persist(sandbox2);

        // When
        List<Sandbox> sandboxes = sandboxRepository.searchAll();

        // Then
        assertNotNull(sandboxes);
        assertEquals(2, sandboxes.size(), "Should return all saved sandboxes");
    }

    @Test
    void testFindByName() {
        // Given
        sandboxRepository.removeAll();

        Sandbox sandbox1 = new Sandbox(null, "Sandbox 1");
        Sandbox sandbox2 = new Sandbox(null, "Sandbox 2");
        sandboxRepository.persist(sandbox1);
        sandboxRepository.persist(sandbox2);

        // When
        List<Sandbox> sandboxes = sandboxRepository.searchByName("Sandbox 2");

        // Then
        assertNotNull(sandboxes);
        assertEquals(1, sandboxes.size(), "Should return all saved sandboxes");
        assertEquals("Sandbox 2", sandboxes.get(0).getName(), "Should return all saved sandboxes");
    }

    @Test
    void testSave() {
        // Given
        Sandbox sandbox = new Sandbox(null, "Test Sandbox");

        // When
        Sandbox savedSandbox = sandboxRepository.persist(sandbox);

        // Then
        assertNotNull(savedSandbox.getId(), "ID should be generated");
        assertEquals("Test Sandbox", savedSandbox.getName());
    }
}
