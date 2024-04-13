package com.cev.finalproyect.proyectservices.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cev.finalproyect.proyectservices.domain.ResetToken;
import com.cev.finalproyect.proyectservices.domain.User;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {
    
    ResetToken findByToken(String token);

    // Actualizado para usar UUID en lugar de Long
    void deleteByUserId(UUID userId);

    Optional<ResetToken> findByUser(User user);
}
