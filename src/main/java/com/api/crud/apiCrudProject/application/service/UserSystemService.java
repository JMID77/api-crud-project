package com.api.crud.apiCrudProject.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.crud.apiCrudProject.application.dto.UserSystemRequest;
import com.api.crud.apiCrudProject.application.dto.UserSystemResponse;
import com.api.crud.apiCrudProject.application.mapper.UserSystemMapper;
import com.api.crud.apiCrudProject.domain.entity.UserSystem;
import com.api.crud.apiCrudProject.domain.repository.UserSystemRepository;
import com.api.crud.apiCrudProject.infrastructure.exception.UserSystemNotFoundException;

@Service
public class UserSystemService {
    private final UserSystemRepository userSysRepository;
    private final UserSystemMapper userSysMapper;


    public UserSystemService(UserSystemRepository userSysRepo, UserSystemMapper userSysMapper) {
        this.userSysRepository = userSysRepo;
        this.userSysMapper = userSysMapper;
    }

    public UserSystemResponse createUserSystem(UserSystemRequest userRequest) {
        var user = this.userSysRepository.save(this.userSysMapper.toEntity(userRequest));
        return this.userSysMapper.toResponse(user);
    }

    public UserSystemResponse updateUserSystem(Long id, UserSystemRequest userSysRequest) {
        UserSystem existingUserSys = this.userSysRepository.findById(id).orElse(null);
        if (existingUserSys == null || existingUserSys.getId() != id) {
            throw new UserSystemNotFoundException("The UserSystem "+id+" not the correct user", id);
        }

        UserSystem theUserSys = this.userSysMapper.toEntity(userSysRequest);

        theUserSys.setId(id);

        var user = this.userSysRepository.save(theUserSys);
        return this.userSysMapper.toResponse(user);
    }

    public Optional<UserSystemResponse> getUserSystem(Long id) {
        return this.userSysRepository.findById(id).map(this.userSysMapper::toResponse);
    }

    public List<UserSystemResponse> getAllUserSystems() {
        return this.userSysRepository.findAll().stream().map(this.userSysMapper::toResponse).toList();
    }

    public boolean deleteUserSystem(Long id) {
        if (this.userSysRepository.existsById(id)) {
            this.userSysRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
