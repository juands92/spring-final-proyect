package com.cev.finalproyect.proyectservices.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cev.finalproyect.proyectservices.domain.Event;
import com.cev.finalproyect.proyectservices.repository.EventRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventPersistanceService {
	
	private final EventRepository eventRepository;

    
    public EventPersistanceService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event getEvent(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with ID: " + id));
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        Event event = getEvent(id);
        event.setTitle(updatedEvent.getTitle());
        event.setDate(updatedEvent.getDate());
        event.setDescription(updatedEvent.getDescription());
        event.setHome(updatedEvent.getHome());
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new EntityNotFoundException("Event not found with ID: " + id);
        }
        eventRepository.deleteById(id);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}
