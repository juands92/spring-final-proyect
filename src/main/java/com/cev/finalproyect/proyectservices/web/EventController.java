package com.cev.finalproyect.proyectservices.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cev.finalproyect.proyectservices.domain.Event;
import com.cev.finalproyect.proyectservices.service.EventPersistanceService;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventPersistanceService eventService;

    @Autowired
    public EventController(EventPersistanceService eventService) {
        this.eventService = eventService;
    }
    
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        Event event = eventService.getEvent(id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event newEvent = eventService.createEvent(event);
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        Event updatedEvent = eventService.updateEvent(id, event);
        return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Mostrar eventos pendientes

    @GetMapping("/pending")
    public ResponseEntity<List<Event>> getPendingEvents() {
        List<Event> pendingEvents = eventService.getPendingEvents();
        return new ResponseEntity<>(pendingEvents, HttpStatus.OK);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Event> completeEvent(@PathVariable Long id){
        Event completedEvent = eventService.completeEvent(id);
        return ResponseEntity.ok(completedEvent);
    }
 
}
