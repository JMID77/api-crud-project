package com.api.crud.apiCrudProject.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.api.crud.apiCrudProject.domain.entity.Language;
import com.api.crud.apiCrudProject.domain.entity.UserSystem;
import com.api.crud.apiCrudProject.domain.repository.UserSystemRepository;

@Service
public class UserSystemTechnicalService {

    @Autowired
    private UserSystemRepository userSysRepository;

    public UserSystem getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String userName = authentication.getName();

        return this.userSysRepository.searchByUsername(userName)
                                            .orElse(null);
    }

    public String getCurrentUserLanguage() {
        UserSystem user = getCurrentUser();

        if (user != null) {
            return user.getLanguage().getCode();
        }
        return Language.ENGLISH.getCode();
    }

    // public UserDetails getCurrentUser() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     return (UserDetails) authentication.getPrincipal();
    // }
}
