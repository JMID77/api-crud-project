package com.api.crud.apiCrudProject.infrastructure.configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.api.crud.apiCrudProject.domain.entity.enumeration.Language;
import com.api.crud.apiCrudProject.domain.entity.enumeration.RoleType;
import com.api.crud.apiCrudProject.domain.entity.UserSystem;
import com.api.crud.apiCrudProject.domain.repository.UserSystemRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseInitializer {
    private final UserSystemRepository userSysRepository;

    public DatabaseInitializer(UserSystemRepository  userSysRepository) {
        this.userSysRepository = userSysRepository;
    }

    @PostConstruct
    public void init() {
        // Vérification que la base de données est vide avant l'initialisation
        if (userSysRepository.searchAll().isEmpty()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            userSysRepository.persist(new UserSystem(null, "adminEn", encoder.encode("admin123"), Language.ENGLISH, RoleType.ROLE_ADMIN));
            userSysRepository.persist(new UserSystem(null, "adminFr", encoder.encode("admin123"), Language.FRENCH, RoleType.ROLE_ADMIN));
            userSysRepository.persist(new UserSystem(null, "userEn", encoder.encode("user123"), Language.ENGLISH, RoleType.ROLE_USER));
            userSysRepository.persist(new UserSystem(null, "userFr", encoder.encode("user123"), Language.FRENCH, RoleType.ROLE_USER));

            // Optional<UserSystem> userSys = userSysRepository.searchByUsername("adminEn");
            // userSys.ifPresent(user -> {
            //     System.out.println("UserSys >> "+user.getUsername()+"/"+user.getRole().name()+" (PWD="+user.getPassword()+")");
            // });
        }
    }
}
