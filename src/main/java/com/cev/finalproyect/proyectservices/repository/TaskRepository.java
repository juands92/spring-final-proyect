package com.cev.finalproyect.proyectservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cev.finalproyect.proyectservices.domain.Task;

import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Long>{

    List<Task> findByCompleted(boolean completed);

    List<Task> findByCompletedFalse();

}
