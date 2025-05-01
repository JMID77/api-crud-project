package com.api.crud.apiCrudProject.infrastructure.configuration;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.api.crud.apiCrudProject.domain.entity.Language;
import com.api.crud.apiCrudProject.domain.entity.RoleType;
import com.api.crud.apiCrudProject.domain.entity.UserSystem;
import com.api.crud.apiCrudProject.domain.repository.UserSystemRepository;

import jakarta.annotation.PostConstruct;

@Component
@Profile("dev")  // Cette classe ne sera activée que si le profil 'dev' est actif
public class DatabaseInitializer {
    private final UserSystemRepository userSysRepository;
    // private final JpaUserSystemRepository jpaUserSysRepository;

    public DatabaseInitializer(UserSystemRepository  userSysRepository) {
        this.userSysRepository = userSysRepository;
    }
    // public DatabaseInitializer(JpaUserSystemRepository jpaUserSysRepository) {
    //     this.jpaUserSysRepository = jpaUserSysRepository;
    // }

    @PostConstruct
    public void init() {
        // Vérification que la base de données est vide avant l'initialisation
        if (userSysRepository.retrieveAll().isEmpty()) {
            userSysRepository.save(new UserSystem(null, "adminEn", "admin123", Language.ENGLISH, RoleType.ROLE_ADMIN));
            userSysRepository.save(new UserSystem(null, "adminFr", "admin123", Language.FRENCH, RoleType.ROLE_ADMIN));
            userSysRepository.save(new UserSystem(null, "userEn", "user123", Language.ENGLISH, RoleType.ROLE_USER));
            userSysRepository.save(new UserSystem(null, "userFr", "user123", Language.FRENCH, RoleType.ROLE_USER));

            Optional<UserSystem> userSys = userSysRepository.searchByUsername("adminEn");
            userSys.ifPresent(user -> {
                System.out.println("UserSys >> "+user.getUsername()+"/"+user.getRole().name());
            });
            
            
            // List<UserSystem> userSysAll = userSysRepository.findAll();
            
            // for (UserSystem userSys : userSysAll) {
            //     System.out.println("UserSys >> "+userSys.getUsername()+"/"+userSys.getRole().name());
            // }
        }
    }
}
