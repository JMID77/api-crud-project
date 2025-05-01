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
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public List<User> retrieveAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return this.userRepository.existsById(id);
    }
}
