package com.cev.finalproyect.proyectservices.service;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cev.finalproyect.proyectservices.domain.User;
import com.cev.finalproyect.proyectservices.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserPersistanceService {
	
	private final UserRepository userRepository;

	@Autowired
	public UserPersistanceService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User getUser(UUID id) {
		return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
	}
	
	public User createUser(User user) {
		String email = user.getEmail();
		if(userRepository.findByEmail(email).isPresent()) {
			throw new RuntimeException("Email already registered");
		}
		return userRepository.save(user);
	}
	
	 public User updateUser(UUID id, User updatedUser) {
		User user = getUser(id);
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setDateOfBirth(updatedUser.getDateOfBirth());
        user.setLastName(updatedUser.getLastName());
        user.setHome(updatedUser.getHome());
		return userRepository.save(user);
    }
	
	public void deleteUser(UUID id) {
	    if (!userRepository.existsById(id)) {
	        throw new EntityNotFoundException("User not found with ID: " + id);
	    }
	    userRepository.deleteById(id);
	}
	
   public List<User> getAllusers() {
        return userRepository.findAll();
    }
   
}
