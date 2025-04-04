package com.api.crud.apiCrudProject.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        // Utilisation de message properties selon la langue (FR/EN) définie
        // Utiliser également comme suit
        //      import org.springframework.context.MessageSource;
        //      return messageSource.getMessage("notblank.message", null, locale);
        @NotBlank(message = "{notblank.username.message}")
        @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit contenir entre 3 et 50 caractères")
        String username,

        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "L'email doit être valide")
        String email,

        @NotBlank(message = "Le mot de passe est obligatoire")
        @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
        String password
) {}
