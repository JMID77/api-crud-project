package com.api.crud.apiCrudProject.domain.repository;

import java.util.List;
import java.util.Optional;

import com.api.crud.apiCrudProject.domain.entity.UserSystem;

public interface UserSystemRepository {
    UserSystem save(UserSystem userSys);
    Optional<UserSystem> findById(Long id);
    List<UserSystem> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);

    Optional<UserSystem> findByUsername(String username);
}
