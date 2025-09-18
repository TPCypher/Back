package com.cypher.config;


import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.cypher.exception.UtilisateurNotFoundException;
import com.cypher.model.User;
import com.cypher.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
       User utilisateur = userRepository.findByEmail(email).orElse(null);

        if (utilisateur == null) {
            throw new UtilisateurNotFoundException();
        }

        return org.springframework.security.core.userdetails.User.withUsername(email).password(utilisateur.getPassword()).build();
    }
}