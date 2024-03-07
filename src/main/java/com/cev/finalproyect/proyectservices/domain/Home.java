package com.cev.finalproyect.proyectservices.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Home {
	@Id
	@GeneratedValue
	Long id;
	String name;
	
	@OneToMany(mappedBy = "home")
	@JsonManagedReference
	List<User> users;
	
	@OneToMany(mappedBy = "home")
	@JsonManagedReference
	List<Task> tasks;
	
	@OneToMany(mappedBy = "home")
	@JsonManagedReference
	List<Expense> expenses;
	
	@OneToMany(mappedBy = "home")
	@JsonManagedReference
	List<Event> events;
	
	
	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
}
