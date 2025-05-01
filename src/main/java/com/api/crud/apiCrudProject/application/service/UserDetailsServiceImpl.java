package com.api.crud.apiCrudProject.application.service;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.api.crud.apiCrudProject.domain.entity.UserSystem;
import com.api.crud.apiCrudProject.domain.repository.UserSystemRepository;


public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserSystemRepository userRepository;

    public UserDetailsServiceImpl(@Lazy UserSystemRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSystem user = userRepository.searchByUsername(username)
                                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        return new org.springframework.security.core.userdetails.User(
                            user.getUsername(),
                            user.getPassword(),
                            List.of(new SimpleGrantedAuthority(user.getRole().name()))
                        );
    }
}
