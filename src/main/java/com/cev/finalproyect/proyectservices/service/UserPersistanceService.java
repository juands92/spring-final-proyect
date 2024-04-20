package com.cev.finalproyect.proyectservices.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cev.finalproyect.proyectservices.domain.Home;
import com.cev.finalproyect.proyectservices.domain.User;
import com.cev.finalproyect.proyectservices.repository.UserRepository;
import com.cev.finalproyect.proyectservices.utils.Utils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserPersistanceService {
	
	private final UserRepository userRepository;
	
	private final Utils utils;

	@Autowired
	public UserPersistanceService(UserRepository userRepository, Utils utils) {
		this.userRepository = userRepository;
		this.utils = utils;
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
	
	
	@Transactional
	public User updateUser(UUID id, Map<String, Object> updates) {
	    User user = getUser(id);

	    updates.forEach((key, value) -> {
	        switch (key) {
	            case "name":
	                user.setName((String) value);
	                break;
	            case "lastName":
	                user.setLastName((String) value);
	                break;
	            case "email":
	                user.setEmail((String) value);
	                break;
	            case "dateOfBirth":
	            	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            	    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            	    try {
            	        Date date = formatter.parse((String) value);
            	        user.setDateOfBirth(date);
            	    } catch (ParseException e) {
            	        throw new IllegalArgumentException("Invalid date format", e);
            	    }
            	    break;
	            case "profileImage":
	            	String imageData = (String) value;
            	   if (imageData != null && imageData.startsWith("data:image")) {
            	        imageData = imageData.substring(imageData.indexOf(",") + 1);
            	    }
            	    try {
            	        byte[] imageBytes = Base64.getDecoder().decode(imageData);
            	        user.setProfileImage(imageBytes);
            	    } catch (IllegalArgumentException e) {
  
            	        System.out.println("Failed to decode Base64 String: " + e.getMessage());
            	    }
            	    break;
	            case "home":
	            	if (value instanceof Map) {
	            		@SuppressWarnings("unchecked")
						Map<String, Object> homeMap = (Map<String, Object>) value;
	            		Home home = utils.convertMapToHome(homeMap);
	            		user.setHome(home);
            	    }
            	    break;
	        }
	    });

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
