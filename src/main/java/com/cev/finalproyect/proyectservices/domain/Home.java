package com.cev.finalproyect.proyectservices.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Home {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "binary(16)", updatable = false, nullable = false)
    
    private UUID id;
    private String name;
    private String address;
    
    // Usuarios con cascada en todas las operaciones y carga perezosa
    @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;
    
    public Home getHome() {
		return home;
	}

	public void setHome(Home home) {
		this.home = home;
	}

	// Gastos con cascada en todas las operaciones y carga perezosa
    @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Expense> expenses; // Gastos asociados al hogar
   
    
    @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;  // Añadido para las tareas
    
    @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Event> events = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_id") // vincula el usuario a un hogar específico.
    private Home home;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users; 
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
