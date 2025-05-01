package com.api.crud.apiCrudProject.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.api.crud.apiCrudProject.domain.entity.UserSystem;
import com.api.crud.apiCrudProject.domain.repository.UserSystemRepository;

@Repository
@Qualifier("userSysSpecificRepo")
public class JpaUserSystemRepositoryImpl implements UserSystemRepository {
    
    private JpaUserSystemRepository userSysRepository;

    
    public JpaUserSystemRepositoryImpl(@Lazy JpaUserSystemRepository userSysRepository) {
        this.userSysRepository = userSysRepository;
    }

    
    @Override
    public UserSystem save(UserSystem userSys) {
        return this.userSysRepository.save(userSys);
    }

    @Override
    public Optional<UserSystem> findById(Long id) {
        return this.userSysRepository.findById(id);
    }

    @Override
    public List<UserSystem> retrieveAll() {
        return this.userSysRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        this.userSysRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return this.userSysRepository.existsById(id);
    }

    @Override
    public Optional<UserSystem> searchByUsername(String username) {
        return this.userSysRepository.findByUsername(username);
    }
}
