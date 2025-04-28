package com.api.crud.apiCrudProject.interaction.controller;

import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.crud.apiCrudProject.application.dto.UserRequest;
import com.api.crud.apiCrudProject.application.dto.UserResponse;
import com.api.crud.apiCrudProject.application.service.UserService;
import com.api.crud.apiCrudProject.infrastructure.exception.UserNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vES.1/users")   // Version vES.1 => version external service 1
public class UserController {
    private final UserService userService;
    
    @Autowired
    private Environment env;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody @Valid UserRequest request) {
        System.out.println("Active profiles (Controller - POST): " + Arrays.toString(this.env.getActiveProfiles()));
        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        System.out.println("Active profiles (Controller - GETID): " + Arrays.toString(this.env.getActiveProfiles()));
        return userService.getUser(id)
                        .orElseThrow(() -> new UserNotFoundException("Utilisateur avec ID " + id + " non trouvé", id));
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        System.out.println("Active profiles (Controller - GETALL): " + Arrays.toString(this.env.getActiveProfiles()));
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        System.out.println("Active profiles (Controller - DELETEID): " + Arrays.toString(this.env.getActiveProfiles()));
        userService.deleteUser(id);
    }

}
