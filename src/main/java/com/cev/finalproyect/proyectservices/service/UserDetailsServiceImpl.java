package com.cev.finalproyect.proyectservices.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cev.finalproyect.proyectservices.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UUID userId = UUID.fromString(id);
        return userRepository.findUserById(userId)
                .map(user -> new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    true, true, true, true,
                    user.getAuthorities()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));
    }
}