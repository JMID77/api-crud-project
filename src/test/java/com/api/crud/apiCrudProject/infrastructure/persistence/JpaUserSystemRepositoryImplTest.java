package com.api.crud.apiCrudProject.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.api.crud.apiCrudProject.domain.entity.enumeration.Language;
import com.api.crud.apiCrudProject.domain.entity.enumeration.RoleType;
import com.api.crud.apiCrudProject.domain.entity.UserSystem;
import com.api.crud.apiCrudProject.domain.repository.UserSystemRepository;

@SpringBootTest
public class JpaUserSystemRepositoryImplTest {

    @Autowired
    private UserSystemRepository userSysRepository;
    @Autowired
    private JpaUserSystemRepository userSysRepo;

    @Test
    void userSysRepository_shouldBeInstanceOfJpaUserSystemRepositoryImpl() {
        assertNotNull(userSysRepository);
        assertTrue(userSysRepository instanceof JpaUserSystemRepositoryImpl,
                        "userSysRepository should be an instance of JpaUserSystemRepositoryImpl");
        // assertEquals(JpaUserSystemRepositoryImpl.class, userSysRepository.getClass(), 
        //     "userSysRepository should be an instance of JpaUserSystemRepositoryImpl");

        assertNotNull(userSysRepo);
        assertInstanceOf(JpaUserSystemRepository.class, userSysRepo,
                        "userSysRepo should be an instance of JpaUserSystemRepository");
    }

    @Test
    void save_shouldPersistUserSystem() {
        UserSystem userSys = UserSystem.builder()
                .username("USER_ADMIN")
                .role(RoleType.ROLE_ADMIN)
                .language(Language.FRENCH)
                .password("password")
                .build();
        
        UserSystem saved = userSysRepository.persist(userSys);

        assertNotNull(saved.getId(), "ID should be generated");
        assertEquals("USER_ADMIN", saved.getUsername(), "UserName not match");
        assertEquals("password", saved.getPassword(), "Password not match");
        assertEquals(Language.FRENCH.name(), saved.getLanguage().name(), "Language not match");
        assertEquals(RoleType.ROLE_ADMIN.name(), saved.getRole().name(), "RoleType not match");
    }


    // @Test
    // void findAll_shouldReturnAllUserSystem() {
    //     UserSystem u1 = UserSystem.builder().username("U1").role(RoleType.ADMIN).password("P1").build();
    //     UserSystem u2 = UserSystem.builder().username("U2").role(RoleType.USER).password("P2").build();

    //     JpaUserSystemRepository userSysRepoTemp = (JpaUserSystemRepository) this.userSysRepo;

    //     userSysRepoTemp.save(u1);
    //     userSysRepoTemp.save(u2);

    //     List<UserSystem> all = userSysRepoTemp.findAll();
    //     // userSysRepo.save(u1);
    //     // userSysRepo.save(u2);

    //     // List<UserSystem> all = userSysRepo.findAll();
    //     assertEquals(2, all.size());
    //     assertEquals("U1", all.get(0).getUsername(), "UserName not match");
    //     assertEquals("P1", all.get(0).getPassword(), "Password not match");
    //     assertEquals(RoleType.ADMIN.name(), all.get(0).getRole().name(), "RoleType not match");
    // }
}
