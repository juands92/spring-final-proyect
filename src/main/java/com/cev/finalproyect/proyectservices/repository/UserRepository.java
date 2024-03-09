package com.cev.finalproyect.proyectservices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cev.finalproyect.proyectservices.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	 Optional<User> findByEmail(String email);
}
