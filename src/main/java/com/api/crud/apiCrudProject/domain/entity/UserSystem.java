package com.api.crud.apiCrudProject.domain.entity;


import com.api.crud.apiCrudProject.domain.entity.converter.LanguageConverter;
import com.api.crud.apiCrudProject.domain.entity.enumeration.Language;
import com.api.crud.apiCrudProject.domain.entity.enumeration.RoleType;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "system_users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserSystem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Column(nullable = false, length = 200)
    private String password;

    // @Enumerated(EnumType.STRING)
    @Convert(converter = LanguageConverter.class)
    @Column(nullable = false)
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;
}
