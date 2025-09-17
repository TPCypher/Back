package com.cypher.config;


import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.cypher.model.User;
import com.cypher.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
       User utilisateur = userRepository.findByUsername(username).orElse(null);

        if (utilisateur == null) {
            throw new RuntimeException("Utilisateur inconnu");
        }

        return org.springframework.security.core.userdetails.User.withUsername(username).password(utilisateur.getPassword()).build();
    }
}