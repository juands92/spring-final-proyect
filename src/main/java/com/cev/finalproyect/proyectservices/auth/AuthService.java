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
import java.util.Date;

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

        String token = jwtService.getToken(userDetails);

        // Obtener los datos del usuario y devolverlos junto con el token
        String username = userDetails.getUsername();
        String name = ((User) userDetails).getName();
        String lastName = ((User) userDetails).getLastName();
        Date dateOfBirth = ((User) userDetails).getDateOfBirth();

        return AuthResponse.builder()
                .token(token)
                .username(username)
                .name(name)
                .lastName(lastName)
                .dateOfBirth(dateOfBirth)
                .build();
    }
    public AuthResponse register(RegisterRequest request) {
        // Verificar si el correo electrónico ya está en uso
        String email = request.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DataIntegrityViolationException("Email already registered");
        }

        // Crear un nuevo usuario
        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .termsAccepted(request.getTermsAccepted())
                .role("USER")
                .build();

        // Guardar el nuevo usuario en la base de datos
        userRepository.save(user);

        // Obtener los detalles del usuario registrado
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );

        // Generar el token JWT con los detalles del usuario registrado
        String token = jwtService.getToken(userDetails);

        // Devolver los datos del usuario y el token en la respuesta
        return AuthResponse.builder()
                .token(token)
                .username(user.getEmail())
                .name(user.getName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .build();
    }

}
