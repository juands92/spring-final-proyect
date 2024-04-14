package com.cev.finalproyect.proyectservices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cev.finalproyect.proyectservices.domain.Event;
import com.cev.finalproyect.proyectservices.repository.EventRepository;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class EventPersistanceService {
	
	private final EventRepository eventRepository;

    @Autowired
    public EventPersistanceService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event getEvent(UUID id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with ID: " + id));
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(UUID id, Event updatedEvent) {
        Event event = getEvent(id);
        event.setTitle(updatedEvent.getTitle());
        event.setDate(updatedEvent.getDate());
        event.setDescription(updatedEvent.getDescription());
        event.setHome(updatedEvent.getHome());
        return eventRepository.save(event);
    }

    public void deleteEvent(UUID id) {
        if (!eventRepository.existsById(id)) {
            throw new EntityNotFoundException("Event not found with ID: " + id);
        }
        eventRepository.deleteById(id);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}
