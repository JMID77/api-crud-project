package com.api.crud.apiCrudProject.infrastructure.security;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.api.crud.apiCrudProject.domain.entity.UserSystem;
import com.api.crud.apiCrudProject.domain.repository.UserSystemRepository;

import org.springframework.security.core.userdetails.User;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserSystemRepository userSystemRepository;  // Injectez ici votre repository UserSystem

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Récupérer l'utilisateur de la base de données
        Optional<UserSystem> userSystem = userSystemRepository.findByUsername(username); // Supposons que la méthode existe

        // Vérifier si l'utilisateur est présent
        if (!userSystem.isPresent()) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec le nom d'utilisateur : " + username);
        }

        // Récupérer l'utilisateur de l'Optional
        UserSystem userSys = userSystem.get();
        
        // Créer un objet UserDetails basé sur l'utilisateur
        return new User(
                userSys.getUsername(),
                userSys.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(userSys.getRole().name())) // Adapter cette ligne selon votre structure de rôles
        );
    }
}
