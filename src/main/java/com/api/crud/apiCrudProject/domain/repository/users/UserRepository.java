package com.api.crud.apiCrudProject.domain.repository.users;

import java.util.List;
import java.util.Optional;

import com.api.crud.apiCrudProject.domain.entity.users.User;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    void deleteById(Long id);
    
}
