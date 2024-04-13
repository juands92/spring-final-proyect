package com.cev.finalproyect.proyectservices.service;


import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cev.finalproyect.proyectservices.domain.User;
import com.cev.finalproyect.proyectservices.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserPersistenceService {
	
	private UserRepository userRepository = null;

    public UserPersistenceService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
    }
    
    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }
    
    @Transactional
    public User updateUser(UUID id, User updatedUser) {
        User user = getUser(id);
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        //user.setDateOfBirth(updatedUser.getDateOfBirth());
        user.setLastName(updatedUser.getLastName());
        user.setHome(updatedUser.getHome());
        return userRepository.save(user);
    }
    
    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    public UUID getUserIdByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
        return user.getId();
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}