package com.api.crud.apiCrudProject.application.dto.action;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.crud.apiCrudProject.application.dto.actions.ActionRequest;
import com.api.crud.apiCrudProject.domain.entity.actions.ActionStatus;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;


public class ActionRequestValidationTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @Test
    public void whenValid_thenNoViolations() {
        ActionRequest request = createActionRequest("Faire les courses", ActionStatus.CREATED);
        System.out.println("Validating request: " + request);
        Set<ConstraintViolation<ActionRequest>> violations = this.validator.validate(request);
        System.out.println("Validationg violations: "+violations);
        assertTrue(violations.isEmpty(), "Expected no constraint violations, but found: " + violations);
    }

    @Test
    public void whenActionNameIsBlank_thenViolationOccurs() {
        ActionRequest request = createActionRequest("     ", ActionStatus.CREATED);
        Set<ConstraintViolation<ActionRequest>> violations = this.validator.validate(request);

        assertFalse(violations.isEmpty(), "Expected constraint violations, but found none.");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("actionName")), "Expected violation on actionName, but found: " + violations);
    }

    @Test
    public void whenActionNameIsTooLong_thenViolationOccurs() {
        String longName = "a".repeat(101); // Assuming the max length is 100 characters
        ActionRequest request = createActionRequest(longName, ActionStatus.CREATED);
        Set<ConstraintViolation<ActionRequest>> violations = this.validator.validate(request);

        assertFalse(violations.isEmpty(), "Expected constraint violations, but found none.");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("actionName")), "Expected violation on actionName, but found: " + violations);
    }

    @Test
    public void whenActionStatusIsNull_thenViolationOccurs() {
        ActionRequest request = createActionRequest("Faire les courses", null);
        Set<ConstraintViolation<ActionRequest>> violations = this.validator.validate(request);

        assertFalse(violations.isEmpty(), "Expected constraint violations, but found none.");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("actionStatus")), "Expected violation on status, but found: " + violations);
    }

    @Test
    public void whenActionNameIsNull_thenViolationOccures() {
        ActionRequest request = createActionRequest(null, ActionStatus.CREATED);
        Set<ConstraintViolation<ActionRequest>> violations = this.validator.validate(request);

        assertFalse(violations.isEmpty(), "Expected constraint violations, but found none.");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("actionName")), "Expected violation on actionName, but found: " + violations);
    }

    private ActionRequest createActionRequest(String actionName, ActionStatus status) {
        return new ActionRequest(actionName, status);
    }

}
