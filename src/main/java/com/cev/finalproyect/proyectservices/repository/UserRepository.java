package com.cev.finalproyect.proyectservices.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cev.finalproyect.proyectservices.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> { // Cambio de Long a UUID
    Optional<User> findByEmail(String email); // Método para buscar por email
    boolean existsByEmail(String email); // Método para verificar si existe un email
}
