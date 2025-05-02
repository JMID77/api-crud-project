package com.api.crud.apiCrudProject.domain.repository;

import java.util.List;
import java.util.Optional;

import com.api.crud.apiCrudProject.domain.entity.UserSystem;

public interface UserSystemRepository {
    UserSystem persist(UserSystem userSys);
    Optional<UserSystem> searchById(Long id);
    List<UserSystem> searchAll();
    void removeById(Long id);
    boolean checkById(Long id);

    Optional<UserSystem> searchByUsername(String username);
}
