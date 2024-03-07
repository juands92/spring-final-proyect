package com.cev.finalproyect.proyectservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cev.finalproyect.proyectservices.domain.Event;

public interface EventRepository extends JpaRepository<Event, Long>{

}
