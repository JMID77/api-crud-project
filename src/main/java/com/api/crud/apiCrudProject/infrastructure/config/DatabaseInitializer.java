package com.api.crud.apiCrudProject.infrastructure.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.api.crud.apiCrudProject.domain.entity.Language;
import com.api.crud.apiCrudProject.domain.entity.RoleType;
import com.api.crud.apiCrudProject.domain.entity.UserSystem;
import com.api.crud.apiCrudProject.infrastructure.persistence.JpaUserSystemRepository;

import jakarta.annotation.PostConstruct;

@Component
@Profile("dev")  // Cette classe ne sera activée que si le profil 'dev' est actif
public class DatabaseInitializer {
 private final JpaUserSystemRepository jpaUserSysRepository;

    public DatabaseInitializer(JpaUserSystemRepository jpaUserSysRepository) {
        this.jpaUserSysRepository = jpaUserSysRepository;
    }

    @PostConstruct
    public void init() {
        // Vérification que la base de données est vide avant l'initialisation
        if (jpaUserSysRepository.count() == 0) {
            jpaUserSysRepository.save(new UserSystem(null, "adminEn", "admin123", Language.ENGLISH, RoleType.ROLE_ADMIN));
            jpaUserSysRepository.save(new UserSystem(null, "adminFr", "admin456", Language.FRENCH, RoleType.ROLE_ADMIN));
            jpaUserSysRepository.save(new UserSystem(null, "userEn", "user123", Language.ENGLISH, RoleType.ROLE_USER));
            jpaUserSysRepository.save(new UserSystem(null, "userFr", "user456", Language.FRENCH, RoleType.ROLE_USER));
        }
    }
}
