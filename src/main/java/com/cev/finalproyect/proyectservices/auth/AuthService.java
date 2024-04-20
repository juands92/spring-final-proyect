package com.cev.finalproyect.proyectservices.auth;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cev.finalproyect.proyectservices.domain.User;
import com.cev.finalproyect.proyectservices.jwt.JwtService;
import com.cev.finalproyect.proyectservices.repository.UserRepository;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder,  AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails userDetails = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.getToken(String.valueOf(((User) userDetails).getId()));
        UUID id = ((User) userDetails).getId();

        return AuthResponse.builder()
        		.id(id)
                .token(token)
                .build();
    }
    public AuthResponse register(RegisterRequest request) {
        String email = request.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DataIntegrityViolationException("Email already registered");
        }


        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .termsAccepted(request.getTermsAccepted())
                .role("USER")
                .build();

   
        userRepository.save(user);

        String token = jwtService.getToken(String.valueOf(user.getId()));

        return AuthResponse.builder()
        		.id(user.getId())
                .token(token)
                .build();
    }

}
