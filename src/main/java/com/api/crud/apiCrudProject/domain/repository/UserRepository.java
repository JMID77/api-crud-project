package com.api.crud.apiCrudProject.domain.repository;

import java.util.List;
import java.util.Optional;

import com.api.crud.apiCrudProject.domain.entity.User;

public interface UserRepository {
    User persist(User user);
    Optional<User> searchById(Long id);
    List<User> searchAll();
    void removeById(Long id);
    boolean checkById(Long id);
}
