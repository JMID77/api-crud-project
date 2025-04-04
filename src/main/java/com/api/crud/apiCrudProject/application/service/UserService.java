package com.api.crud.apiCrudProject.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.crud.apiCrudProject.application.dto.UserRequest;
import com.api.crud.apiCrudProject.application.dto.UserResponse;
import com.api.crud.apiCrudProject.application.mapper.UserMapper;
import com.api.crud.apiCrudProject.domain.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse createUser(UserRequest userRequest) {
        var user = this.userRepository.save(userMapper.toEntity(userRequest));
        return userMapper.toResponse(user);
    }

    public Optional<UserResponse> getUser(Long id) {
        return this.userRepository.findById(id).map(userMapper::toResponse);
    }

    public List<UserResponse> getAllUsers() {
        return this.userRepository.findAll().stream().map(userMapper::toResponse).toList();
    }

    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }
}
