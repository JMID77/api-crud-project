package com.api.crud.apiCrudProject.application.dto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.crud.apiCrudProject.domain.entity.enumeration.Language;
import com.api.crud.apiCrudProject.domain.entity.enumeration.RoleType;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UserSystemRequestTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validRequest_shouldPassValidation() {
        // Given
        UserSystemRequest request = new UserSystemRequest(
            "validUser", "strongPassword", Language.FRENCH, RoleType.ROLE_USER
        );

        // When
        Set<ConstraintViolation<UserSystemRequest>> violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void blankUsername_shouldFailValidation() {
        UserSystemRequest request = new UserSystemRequest(
            "  ", "strongPassword", Language.FRENCH, RoleType.ROLE_USER
        );

        Set<ConstraintViolation<UserSystemRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")));
    }

    @Test
    void nullPassword_shouldFailValidation() {
        UserSystemRequest request = new UserSystemRequest(
            "validUser", null, Language.FRENCH, RoleType.ROLE_USER
        );

        Set<ConstraintViolation<UserSystemRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    void tooLongUsername_shouldFailValidation() {
        String longUsername = "a".repeat(51); // dépasse la limite de 50
        UserSystemRequest request = new UserSystemRequest(
            longUsername, "strongPassword", Language.FRENCH, RoleType.ROLE_USER
        );

        Set<ConstraintViolation<UserSystemRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")));
    }

    @Test
    void nullLanguage_shouldFailValidation() {
        UserSystemRequest request = new UserSystemRequest(
            "validUser", "strongPassword", null, RoleType.ROLE_USER
        );

        Set<ConstraintViolation<UserSystemRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("language")));
    }

    @Test
    void nullRole_shouldFailValidation() {
        UserSystemRequest request = new UserSystemRequest(
            "validUser", "strongPassword", Language.FRENCH, null
        );

        Set<ConstraintViolation<UserSystemRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("role")));
    }
}
