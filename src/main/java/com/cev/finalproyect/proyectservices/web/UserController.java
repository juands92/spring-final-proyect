package com.cev.finalproyect.proyectservices.web;

import java.util.List;
import java.util.UUID; // Importar UUID

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cev.finalproyect.proyectservices.domain.User;
import com.cev.finalproyect.proyectservices.service.UserPersistenceService; 

@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserPersistenceService userPersistenceService;

    public UserController(UserPersistenceService userPersistenceService) {
        this.userPersistenceService = userPersistenceService;
    }
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userPersistenceService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable UUID id) { 
        User user = userPersistenceService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userPersistenceService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) { 
        User updatedUser = userPersistenceService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable String email) {
        try {
            UUID userId = userPersistenceService.getUserIdByEmail(email);
            if (userId != null) {
                userPersistenceService.deleteUser(userId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}