package com.cev.finalproyect.proyectservices.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Home {
	@Id
	@GeneratedValue
	Long id;
	String name;
	
	@OneToMany(mappedBy = "users")
	@JsonManagedReference
	List<User> users;
	
	@OneToMany(mappedBy = "tasks")
	@JsonManagedReference
	List<Task> tasks;
	
	@OneToMany(mappedBy = "expenses")
	@JsonManagedReference
	List<Expense> expenses;
	
	@OneToMany(mappedBy = "events")
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
