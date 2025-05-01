package com.api.crud.apiCrudProject.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.crud.apiCrudProject.application.dto.UserRequest;
import com.api.crud.apiCrudProject.application.dto.UserResponse;
import com.api.crud.apiCrudProject.application.mapper.UserMapper;
import com.api.crud.apiCrudProject.domain.entity.User;
import com.api.crud.apiCrudProject.domain.repository.UserRepository;
import com.api.crud.apiCrudProject.infrastructure.exception.Entities;
import com.api.crud.apiCrudProject.infrastructure.exception.RessourceNotFoundException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse createUser(UserRequest userRequest) {
        var user = this.userRepository.save(this.userMapper.toEntity(userRequest));
        return this.userMapper.toResponse(user);
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        UserResponse userResponse = null;

        if (checkExistsUser(id)) {
            User theUser = this.userMapper.toEntity(userRequest);

            theUser.setId(id);

            var user = this.userRepository.save(theUser);
            userResponse = this.userMapper.toResponse(user);
        }

        return userResponse;
    }

    public UserResponse getUser(Long id) {
        return this.userRepository.findById(id)
                                    .map(this.userMapper::toResponse)
                                    .orElseThrow(() -> new RessourceNotFoundException(Entities.USER, id));
    }

    public List<UserResponse> getAllUsers() {
        return this.userRepository.retrieveAll().stream().map(this.userMapper::toResponse).toList();
    }

    public void deleteUser(Long id) {
        if (checkExistsUser(id)) {
            this.userRepository.deleteById(id);
        }
    }

    private boolean checkExistsUser(Long id) {
        User existingUser = this.userRepository.findById(id).orElse(null);
        if (existingUser == null || existingUser.getId() != id) {
            throw new RessourceNotFoundException(Entities.USER, id);
        }
        return true;
    } 
}
