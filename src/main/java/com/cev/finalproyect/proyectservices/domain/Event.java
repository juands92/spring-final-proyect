package com.cev.finalproyect.proyectservices.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Event {
	@Id
	@GeneratedValue
	Long id;
	String title;
	Long description;
	Date date;
	
	@ManyToOne
	@JsonBackReference
	Home home;
}
