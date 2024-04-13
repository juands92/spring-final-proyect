package com.cev.finalproyect.proyectservices.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String title;
	Long description;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	Date date;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "home_id")
	private Home home;
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	    name = "event_user",  // Nombre de la tabla intermedia
	    joinColumns = @JoinColumn(name = "event_id"),  // Clave foránea que apunta a Event
	    inverseJoinColumns = @JoinColumn(name = "user_id")  // Clave foránea que apunta a User
	)
	private List<User> users = new ArrayList<>();

	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getDescription() {
		return description;
	}

	public void setDescription(Long description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Home getHome() {
		return home;
	}

	public void setHome(Home home) {
		this.home = home;
	}

}
