 package com.cev.finalproyect.proyectservices.auth;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cev.finalproyect.proyectservices.domain.ResetToken;
import com.cev.finalproyect.proyectservices.domain.User;
import com.cev.finalproyect.proyectservices.repository.ResetTokenRepository;
import com.cev.finalproyect.proyectservices.repository.UserRepository;

@RestController
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final ResetTokenRepository resetTokenRepository;
    private final PasswordEncoder passwordEncoder; 

    public AuthController(AuthService authService, UserRepository userRepository, ResetTokenRepository resetTokenRepository, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.resetTokenRepository = resetTokenRepository;
        this.passwordEncoder = passwordEncoder; 
    }
    
    @PostMapping(value = "/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        System.out.println("Intento de inicio de sesión para el correo: " + request.getEmail());
        
        try {
            AuthResponse response = authService.login(request);
            System.out.println("Inicio de sesión exitoso para el correo: " + request.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error durante el inicio de sesión para el correo: " + request.getEmail() + ". Detalle del error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/auth/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    
    @PostMapping("/auth/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        System.out.println("Solicitud de restablecimiento de contraseña recibida para el correo: " + email);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            System.out.println("Correo electrónico no encontrado: " + email);
            return ResponseEntity.badRequest().body("Correo electrónico no encontrado.");
        }

        User user = userOptional.get();
        // Verificar si ya existe un token para este usuario
        Optional<ResetToken> existingTokenOptional = resetTokenRepository.findByUser(user);

        if (existingTokenOptional.isPresent()) {
            // Si ya existe, actualiza el token existente
            ResetToken existingToken = existingTokenOptional.get();
            existingToken.setToken(UUID.randomUUID().toString());
            existingToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));
            resetTokenRepository.save(existingToken);
            System.out.println("Actualizando token existente para usuario: " + email + " actualizando");
            System.out.println("este es el valor del token: " + existingToken.getToken());


            sendResetPasswordEmail(user.getEmail(), existingToken.getToken());
        } else {
            // Si no existe, crea un nuevo token
            String token = UUID.randomUUID().toString();
            ResetToken resetToken = new ResetToken(token, LocalDateTime.now().plusMinutes(30), user);
            resetTokenRepository.save(resetToken);
            System.out.println("Creando nuevo token para usuario: " + email);
            sendResetPasswordEmail(user.getEmail(), token);
        }

        return ResponseEntity.ok("Se ha enviado un correo electrónico para restablecer la contraseña.");
    }

    @PostMapping("/auth/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        System.out.println("Token recibido para restablecimiento: " + request.getToken());

        // Busca el token y verifica si existe y no ha expirado
        Optional<ResetToken> resetTokenOptional = Optional.ofNullable(resetTokenRepository.findByToken(request.getToken()));
        if (!resetTokenOptional.isPresent()) {
            System.out.println("Token de restablecimiento inválido: " + request.getToken());
            return ResponseEntity.badRequest().body("Token de restablecimiento inválido.");
        }

        ResetToken resetToken = resetTokenOptional.get();
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            System.out.println("Token de restablecimiento expirado: " + request.getToken());
            return ResponseEntity.badRequest().body("Token de restablecimiento expirado.");
        }

        // Obtiene el usuario asociado al token y actualiza su contraseña
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword())); // Codifica la nueva contraseña antes de guardarla
        userRepository.save(user);
        
        //Elimina el token de restablecimiento una vez utilizado para evitar su reutilización
        resetTokenRepository.delete(resetToken);
        System.out.println("Contraseña actualizada con éxito para usuario: " + user.getEmail());

        return ResponseEntity.ok("Contraseña actualizada con éxito.");
    }

 // Método para enviar el correo electrónico con el token de restablecimiento
    private void sendResetPasswordEmail(String email, String token) {
    	
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "yokairecordings-com.correoseguro.dinaserver.com");
        properties.put("mail.smtp.port", "465"); // Utiliza el puerto SMTPS: 465
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true"); // Habilita SSL

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication("appcev@yokairecordings.com", "appCev2024_");
            }
        });

        try {
            // Crea un objeto MimeMessage
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("appcev@yokairecordings.com")); 
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Solicitud de restablecimiento de contraseña");
            String resetLink = "http://localhost:4200/reset-password?token=" + token; 
            message.setText("Hola,\n\nPara restablecer tu contraseña, por favor sigue el siguiente enlace:\n" + resetLink + "\n\nEl equipo.");

            Transport.send(message);
            System.out.println("Correo electrónico de restablecimiento de contraseña enviado con éxito a: " + email);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error al enviar el correo electrónico de restablecimiento de contraseña a: " + email + ". Detalle del error: " + e.getMessage());
        }
    }
    @RequestMapping(value = "/auth/register", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptionsRequest() {
        return ResponseEntity.ok().build();
    }
    

	public UserRepository getUserRepository() {
		return userRepository;
	}
	public ResetTokenRepository getResetTokenRepository() {
		return resetTokenRepository;
	}
}
