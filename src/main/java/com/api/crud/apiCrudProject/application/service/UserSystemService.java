package com.api.crud.apiCrudProject.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.crud.apiCrudProject.application.dto.UserSystemRequest;
import com.api.crud.apiCrudProject.application.dto.UserSystemResponse;
import com.api.crud.apiCrudProject.application.mapper.UserSystemMapper;
import com.api.crud.apiCrudProject.domain.entity.UserSystem;
import com.api.crud.apiCrudProject.domain.repository.UserSystemRepository;
import com.api.crud.apiCrudProject.infrastructure.exception.Entities;
import com.api.crud.apiCrudProject.infrastructure.exception.RessourceNotFoundException;

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
        UserSystemResponse userSysResponse = null;

        if (checkExistsUserSystem(id)) {
            UserSystem theUserSys = this.userSysMapper.toEntity(userSysRequest);

            theUserSys.setId(id);
    
            var user = this.userSysRepository.save(theUserSys);
            userSysResponse = this.userSysMapper.toResponse(user);
        }
        
        return userSysResponse;
    }

    public UserSystemResponse getUserSystem(Long id) {
        return this.userSysRepository.findById(id)
                                        .map(this.userSysMapper::toResponse)
                                        .orElseThrow(() -> new RessourceNotFoundException(Entities.USER_SYSTEM, id));
    }

    public List<UserSystemResponse> getAllUserSystems() {
        return this.userSysRepository.retrieveAll().stream().map(this.userSysMapper::toResponse).toList();
    }

    public void deleteUserSystem(Long id) {
        if (checkExistsUserSystem(id)) {
            this.userSysRepository.deleteById(id);
        }
    }

    
    private boolean checkExistsUserSystem(Long id) {
        UserSystem existingUserSys = this.userSysRepository.findById(id).orElse(null);
        if (existingUserSys == null || existingUserSys.getId() != id) {
            throw new RessourceNotFoundException(Entities.USER_SYSTEM, id);
        }
        return true;
    } 
}
