package com.cev.finalproyect.proyectservices.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cev.finalproyect.proyectservices.domain.Home;
import com.cev.finalproyect.proyectservices.domain.User;
import com.cev.finalproyect.proyectservices.repository.HomeRepository;
import com.cev.finalproyect.proyectservices.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class HomePersistanceService {

    private final HomeRepository homeRepository;

    @Autowired
    public HomePersistanceService(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    public Home getHome(Long id) {
        return homeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Home not found with ID: " + id));
    }

    public Home createHome(Home home) {
        return homeRepository.save(home);
    }


    public Home updateHome(Long id, Home updatedHome) {
        Home home = getHome(id);
        home.setName(updatedHome.getName());
        home.setAddress(updatedHome.getAddress());
        return homeRepository.save(home);
    }


    public void deleteHome(Long id) {
        if (!homeRepository.existsById(id)) {
            throw new EntityNotFoundException("Home not found with ID: " + id);
        }
        homeRepository.deleteById(id);
    }
    
    public List<Home> getAllhomes() {
        return homeRepository.findAll();
    }
}