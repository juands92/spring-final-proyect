package com.cev.finalproyect.proyectservices.utils;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.cev.finalproyect.proyectservices.domain.Home;

@Component
public class Utils {
	
	public Home convertMapToHome(Map<String, Object> homeMap) {
	    Home home = new Home();
	    try {
	        if (homeMap.get("id") != null) {
	            UUID homeId = UUID.fromString((String) homeMap.get("id"));
	            home.setId(homeId);
	        }
	    } catch (IllegalArgumentException e) {
	        System.out.println("Error: Invalid UUID format");
	    }
	    return home;
	}
}
