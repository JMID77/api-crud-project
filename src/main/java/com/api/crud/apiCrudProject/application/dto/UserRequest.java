package com.api.crud.apiCrudProject.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        // Utilisation de message properties selon la langue (FR/EN) définie
        // Utiliser également comme suit
        //      import org.springframework.context.MessageSource;
        //      return messageSource.getMessage("notblank.message", null, locale);
        @NotBlank(message = "{username.notblank.message}")
        @Size(min = 3, max = 50, message = "{username.size.message}")
        String userName,

        @NotBlank(message = "{email.notblank.message}")
        @Email(message = "{email.size.message}")
        String email,

        @NotBlank(message = "{password.notblank.message}")
        @Size(min = 6, message = "{password.size.message}")
        String password
) {}
