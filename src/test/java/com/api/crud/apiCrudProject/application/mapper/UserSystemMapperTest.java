package com.api.crud.apiCrudProject.application.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.api.crud.apiCrudProject.application.dto.UserSystemRequest;
import com.api.crud.apiCrudProject.application.dto.UserSystemResponse;
import com.api.crud.apiCrudProject.domain.entity.UserSystem;
import com.api.crud.apiCrudProject.domain.entity.enumeration.Language;
import com.api.crud.apiCrudProject.domain.entity.enumeration.RoleType;


public class UserSystemMapperTest {
private UserSystemMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserSystemMapper();
    }

    @Test
    void toEntity_shouldMapUserSystemRequestToUserSystemEntity() {
        // Given
        UserSystemRequest request = new UserSystemRequest("john.doe", "pass123", Language.ENGLISH, RoleType.ROLE_USER);

        // When
        UserSystem entity = mapper.toEntity(request);

        // Then
        assertNotNull(entity);
        assertNull(entity.getId());
        assertEquals("john.doe", entity.getUsername());
        assertEquals("pass123", entity.getPassword());
        assertEquals(Language.ENGLISH.name(), entity.getLanguage().name());
        assertEquals(RoleType.ROLE_USER.name(), entity.getRole().name());
    }

    @Test
    void toResponse_shouldMapUserSystemEntityToUserSystemResponse() {
        // Given
        UserSystem user = new UserSystem(1L, "john.doe", "pass123", Language.FRENCH, RoleType.ROLE_ADMIN);

        // When
        UserSystemResponse response = mapper.toResponse(user);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("john.doe", response.username());
        assertEquals("pass123", response.password());
        assertEquals(Language.FRENCH.name(), response.language().name());
        assertEquals(RoleType.ROLE_ADMIN.name(), response.role().name());
    }
}
