package com.cev.finalproyect.proyectservices.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cev.finalproyect.proyectservices.domain.Task;


public interface TaskRepository extends JpaRepository<Task, UUID>{

}
