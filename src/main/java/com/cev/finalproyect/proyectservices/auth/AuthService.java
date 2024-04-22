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


import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    private static final long TOKEN_VALIDITY_DEFAULT = 1000 * 60 * 60 * 3; //3 horas
    private static final long TOKEN_VALIDITY_EXTENDED = 5 * 24 * 60 * 60 * 1000; // 5 dias

    //logs duracion y dep
    private static final Logger logger = Logger.getLogger(JwtService.class.getName());


    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder,  AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;

        //logs
        logger.info("Duración predeterminada del token: " + TOKEN_VALIDITY_DEFAULT + " milisegundos");
        logger.info("Duración extendida del token: " + TOKEN_VALIDITY_EXTENDED + " milisegundos");

    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserDetails userDetails = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));


        // Verificar si el usuario ha marcado la opción de Recordar Usuario
        boolean rememberUser = request.isRememberUser();

        // Definir la duración del token basada en Recordar Usuario
        long tokenValidity = rememberUser ? TOKEN_VALIDITY_EXTENDED : TOKEN_VALIDITY_DEFAULT;


        String token = jwtService.getToken(String.valueOf(((User) userDetails).getId()), tokenValidity);
        String email =((User) userDetails).getEmail();
        String name = ((User) userDetails).getName();
        String lastName = ((User) userDetails).getLastName();
        Date dateOfBirth = ((User) userDetails).getDateOfBirth();
        UUID id = ((User) userDetails).getId();
        byte[] profileImage = ((User) userDetails).getProfileImage();
        String base64Image = (profileImage != null) ? Base64.getEncoder().encodeToString(profileImage) : null;



        return AuthResponse.builder()
        		.id(id)
                .token(token)
                .email(email)
                .name(name)
                .lastName(lastName)
                .dateOfBirth(dateOfBirth)
                .profileImage(base64Image)
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

        long tokenValidity = TOKEN_VALIDITY_DEFAULT;

        String token = jwtService.getToken(String.valueOf(user.getId()), tokenValidity);

        return AuthResponse.builder()
        		.id(user.getId())
                .token(token)
                .email(user.getEmail())
                .name(user.getName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .build();
    }

}
