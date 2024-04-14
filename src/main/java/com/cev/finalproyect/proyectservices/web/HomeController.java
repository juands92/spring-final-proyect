package com.cev.finalproyect.proyectservices.web;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.cev.finalproyect.proyectservices.domain.Home;
import com.cev.finalproyect.proyectservices.service.HomePersistanceService;


@RestController
@RequestMapping("/homes")
public class HomeController {

    private final HomePersistanceService homePersistanceService;

    @Autowired
    public HomeController(HomePersistanceService homePersistanceService) {
        this.homePersistanceService = homePersistanceService;
    }
    
    @GetMapping
    public ResponseEntity<List<Home>> getAllHomes() {
        List<Home> homes = homePersistanceService.getAllhomes();
        return new ResponseEntity<>(homes, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Home> getHome(@PathVariable UUID id) {
        Home home = homePersistanceService.getHome(id);
        return new ResponseEntity<>(home, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Home> createHome(@RequestBody Home home) {
        Home newHome = homePersistanceService.createHome(home);
        return new ResponseEntity<>(newHome, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Home> updateHome(@PathVariable UUID id, @RequestBody Home home) {
        Home updatedHome = homePersistanceService.updateHome(id, home);
        return new ResponseEntity<>(updatedHome, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteHome(@PathVariable UUID id) {
        homePersistanceService.deleteHome(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}