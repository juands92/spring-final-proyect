package com.cev.finalproyect.proyectservices.repository;

import com.cev.finalproyect.proyectservices.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cev.finalproyect.proyectservices.domain.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>{

    List<Event> findByCompletedFalse();

    List<Event> findByCompleted(boolean completed);

}
