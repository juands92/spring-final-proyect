package com.cev.finalproyect.proyectservices.web;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cev.finalproyect.proyectservices.domain.User;
import com.cev.finalproyect.proyectservices.service.UserPersistanceService;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserPersistanceService userPersistanceService;

    @Autowired
    public UserController(UserPersistanceService userPersistanceService) {
        this.userPersistanceService = userPersistanceService;
    }
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userPersistanceService.getAllusers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable UUID id) {
        User user = userPersistanceService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userPersistanceService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
  

   @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody Map<String, Object> updates) {
        User updatedUser = userPersistanceService.updateUser(id, updates);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userPersistanceService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    
    @PutMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> uploadUserImage(@PathVariable UUID id, @RequestParam("image") MultipartFile file) throws java.io.IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty, cannot update user image.");
        }

        String imageBase64 = Base64.getEncoder().encodeToString(file.getBytes());
        Map<String, Object> updates = new HashMap<>();
        updates.put("profileImage", "data:image/png;base64," + imageBase64);

        User updatedUser = userPersistanceService.updateUser(id, updates);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}
