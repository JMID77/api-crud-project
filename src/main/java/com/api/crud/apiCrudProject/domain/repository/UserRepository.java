package com.api.crud.apiCrudProject.domain.repository;

import java.util.List;
import java.util.Optional;

import com.api.crud.apiCrudProject.domain.entity.User;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    void deleteById(Long id);
    
}
