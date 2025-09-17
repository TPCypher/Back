package com.cypher.api;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.cypher.config.JwtUtil;
import com.cypher.exception.UtilisateurNotFoundException;
import com.cypher.model.User;
import com.cypher.repository.UserRepository;
import com.cypher.request.RegisterRequest;
import com.cypher.response.AuthResponse;
import com.cypher.response.UserResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/utilisateur")
@RequiredArgsConstructor
@Log4j2
public class UtilisateurApiController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public AuthResponse auth(@Valid @RequestBody RegisterRequest request) {
        try {

            Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

            authentication = this.authenticationManager.authenticate(authentication);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = JwtUtil.generate(this.repository.findByUsername(request.getUsername()).orElseThrow(UtilisateurNotFoundException::new));

            log.debug("Token *** généré!");

         return AuthResponse.builder()
                .success(true)
                .token(token)
                .build()
            ;
        }

        catch (BadCredentialsException e) {
            return AuthResponse.builder()
                .success(false)
                .build()
            ;
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse create(@Valid @RequestBody RegisterRequest request) {
        User user = new User();

        BeanUtils.copyProperties(request, user);

        user.setPassword(this.passwordEncoder.encode(request.getPassword()));

        this.repository.save(user);

        return this.convertAuth(user);
    }

    @GetMapping("/get")
    @PreAuthorize("isAuthenticated()")
    public UserResponse get() {
        String userid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("utilisateurid: " + userid);
        User user = this.repository.findById(userid).orElseThrow(UtilisateurNotFoundException::new);
        return this.convertInfo(user);
    }


    private AuthResponse convertAuth(User user) {
        AuthResponse response = AuthResponse.builder().build();
        BeanUtils.copyProperties(user, response);
        return response;
    }

    private UserResponse convertInfo(User user) {
        UserResponse response = UserResponse.builder().build();
        BeanUtils.copyProperties(user, response);
        return response;
    }
}
