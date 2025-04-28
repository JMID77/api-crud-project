package com.api.crud.apiCrudProject.interaction.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.crud.apiCrudProject.infrastructure.security.CurrentUserService;

@RestController
@RequestMapping("/api/vIS.1/internal")   // Version vIS.1 => version internal service 1
public class UserSystemController {
    
    private final CurrentUserService currentUserService;

    public UserSystemController(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    @GetMapping("/admins/check")
    public String admin() {
        // Récupérer l'utilisateur authentifié
        UserDetails userDetails = currentUserService.getCurrentUser();
        return "Hello Administrator ["+userDetails.getUsername()+"] !";
    }

    @GetMapping("/users/check")
    public String user(@AuthenticationPrincipal UserDetails userDetails) {  // @AuthenticationPrincipal => Récupérer l'utilisateur authentifié
        return "Hello simple User ["+userDetails.getUsername()+"] !";
    }

}
