package com.api.crud.apiCrudProject.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.api.crud.apiCrudProject.domain.entity.User;
import com.api.crud.apiCrudProject.domain.repository.UserRepository;

@Repository
@Qualifier("userSpecificRepo")
public class JpaUserRepositoryImpl implements UserRepository {
    private JpaUserRepository userRepository;
    
    public JpaUserRepositoryImpl(@Lazy JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User persist(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public Optional<User> searchById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public List<User> searchAll() {
        return userRepository.findAll();
    }

    @Override
    public void removeById(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public boolean checkById(Long id) {
        return this.userRepository.existsById(id);
    }
}
