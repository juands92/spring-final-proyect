package com.cev.finalproyect.proyectservices.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cev.finalproyect.proyectservices.repository.UserRepository;

@Service
@Primary

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; 

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.cev.finalproyect.proyectservices.domain.User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return new User(user.getEmail(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
