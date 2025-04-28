package com.api.crud.apiCrudProject.interaction.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.crud.apiCrudProject.application.dto.UserRequest;
import com.api.crud.apiCrudProject.application.dto.UserResponse;
import com.api.crud.apiCrudProject.application.service.UserService;

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
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        System.out.println("Active profiles (Controller - POST): " + Arrays.toString(this.env.getActiveProfiles()));

        UserResponse createdUser = this.userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                        .header("X-App-Name", "ApicCrudProject")
                        .body(createdUser);

    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
        System.out.println("Active profiles (Controller - PUT): " + Arrays.toString(this.env.getActiveProfiles()));

        UserResponse updatedUser = this.userService.updateUser(id, request);

        return ResponseEntity.status(HttpStatus.OK)
                        .header("X-App-Name", "ApicCrudProject")
                        .body(updatedUser);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        System.out.println("Active profiles (Controller - GETID): " + Arrays.toString(this.env.getActiveProfiles()));
        return ResponseEntity.ok(this.userService.getUser(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        System.out.println("Active profiles (Controller - GETALL): " + Arrays.toString(this.env.getActiveProfiles()));

        List<UserResponse> users = this.userService.getAllUsers();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        System.out.println("Active profiles (Controller - DELETEID): " + Arrays.toString(this.env.getActiveProfiles()));
        this.userService.deleteUser(id);
        // Return a 204, if the record doesn't exists, the service throws an exception RessourceNotFound
        return ResponseEntity.noContent().build();
    }
}
