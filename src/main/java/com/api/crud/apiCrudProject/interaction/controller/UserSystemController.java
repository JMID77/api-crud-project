package com.api.crud.apiCrudProject.interaction.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.crud.apiCrudProject.application.dto.UserSystemRequest;
import com.api.crud.apiCrudProject.application.dto.UserSystemResponse;
import com.api.crud.apiCrudProject.application.service.UserSystemService;
import com.api.crud.apiCrudProject.domain.entity.UserSystem;
import com.api.crud.apiCrudProject.infrastructure.security.service.TranslationService;
import com.api.crud.apiCrudProject.infrastructure.security.service.UserSystemTechnicalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vIS.1/internal")   // Version vIS.1 => version internal service 1
public class UserSystemController {
    
    private final UserSystemService userSystemService;
    private final UserSystemTechnicalService currentUserService;
    private final TranslationService translationService;

    public UserSystemController(UserSystemService userSystemService, 
                                            UserSystemTechnicalService currentUserService, 
                                            TranslationService translationService) {
        this.userSystemService = userSystemService;
        this.currentUserService = currentUserService;
        this.translationService = translationService;
    }

    @GetMapping("/admins/check")
    public String admin() {
        // Récupérer l'utilisateur authentifié
        UserSystem user = this.currentUserService.getCurrentUser();
        
        return "Hello Administrator ["+user.getUsername()+"] !\n"+this.translationService.translate("internalization.example.admin.hello");
    }

    @GetMapping("/users/check")
    public String user(@AuthenticationPrincipal UserDetails userDetails) {  // @AuthenticationPrincipal => Récupérer l'utilisateur authentifié
        return "Hello simple User ["+userDetails.getUsername()+"] !\n"+this.translationService.translate("internalization.example.user.hello");
    }


    @PostMapping("/admins/users")
    public ResponseEntity<UserSystemResponse> createUserSystem(@RequestBody @Valid UserSystemRequest request) {
        UserSystemResponse createdUserSys = this.userSystemService.createUserSystem(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                        .header("X-App-Name", "ApicCrudProject")
                        .body(createdUserSys);

    }

    @PutMapping("/admins/users")
    public ResponseEntity<UserSystemResponse> updateUserSystem(@PathVariable Long id, @RequestBody @Valid UserSystemRequest request) {
        UserSystemResponse updatedUserSys = this.userSystemService.updateUserSystem(id, request);

        return ResponseEntity.status(HttpStatus.OK)
                        .header("X-App-Name", "ApicCrudProject")
                        .body(updatedUserSys);

    }

    @GetMapping("/admins/users/{id}")
    public ResponseEntity<UserSystemResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(this.userSystemService.getUserSystem(id));
    }

    @GetMapping("/admins/users")
    public ResponseEntity<List<UserSystemResponse>> getAllUsers() {
        List<UserSystemResponse> userSystems = this.userSystemService.getAllUserSystems();

        if (userSystems.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(userSystems);
    }

    @DeleteMapping("/admins/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        this.userSystemService.deleteUserSystem(id);
        // Return a 204, if the record doesn't exists, the service throws an exception RessourceNotFound
        return ResponseEntity.noContent().build();
    }

}
