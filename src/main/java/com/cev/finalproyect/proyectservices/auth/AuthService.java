package com.cev.finalproyect.proyectservices.auth;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cev.finalproyect.proyectservices.domain.User;
import com.cev.finalproyect.proyectservices.jwt.JwtService;
import com.cev.finalproyect.proyectservices.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, JwtService jwtService,
                       PasswordEncoder passwordEncoder,
                       @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails userDetails = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado."));
        String token = jwtService.getToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .username(userDetails.getUsername())
                .name(((User) userDetails).getName())
                .lastName(((User) userDetails).getLastName())
                //.dateOfBirth(((User) userDetails).getDateOfBirth())
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return AuthResponse.builder().error("El correo electrónico ya está en uso.").build();
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .lastName(request.getLastName())
                //.dateOfBirth(request.getDateOfBirth())
                .termsAccepted(request.getTermsAccepted())
                .role("USER")
                .build();
        userRepository.save(user);

        sendWelcomeEmail(user); // Enviar correo electrónico de bienvenida

        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .username(user.getEmail())
                .build();
    }

    private void sendWelcomeEmail(User user) {
        // Implementación de envío de correo electrónico de bienvenida
        // Código de configuración y envío del correo electrónico
    }

    public ResponseEntity<String> forgotPassword(String email) {
        // Implementación de la funcionalidad de olvido de contraseña
        return ResponseEntity.ok("Correo electrónico de restablecimiento de contraseña enviado correctamente");
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
