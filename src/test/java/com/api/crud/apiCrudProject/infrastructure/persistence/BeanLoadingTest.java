package com.api.crud.apiCrudProject.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BeanLoadingTest {

    @Autowired
    private JpaActionRepositoryImpl jpaActionRepositoryImpl;

    @Test
    void jpaActionRepositoryImpl_shouldBeLoaded() {
        assertNotNull(jpaActionRepositoryImpl);
        assertInstanceOf(JpaActionRepositoryImpl.class, jpaActionRepositoryImpl, "Bean should be of type JpaActionRepositoryImpl");
    }
}
