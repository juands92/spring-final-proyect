package com.cev.finalproyect.proyectservices.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cev.finalproyect.proyectservices.domain.Home;

public interface HomeRepository  extends JpaRepository<Home, UUID>{

}
