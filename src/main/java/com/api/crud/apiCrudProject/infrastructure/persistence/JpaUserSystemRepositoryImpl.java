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
    public UserSystem persist(UserSystem userSys) {
        return this.userSysRepository.save(userSys);
    }

    @Override
    public Optional<UserSystem> searchById(Long id) {
        return this.userSysRepository.findById(id);
    }

    @Override
    public List<UserSystem> searchAll() {
        return this.userSysRepository.findAll();
    }

    @Override
    public void removeById(Long id) {
        this.userSysRepository.deleteById(id);
    }

    @Override
    public boolean checkById(Long id) {
        return this.userSysRepository.existsById(id);
    }

    @Override
    public Optional<UserSystem> searchByUsername(String username) {
        return this.userSysRepository.findByUsername(username);
    }
}
