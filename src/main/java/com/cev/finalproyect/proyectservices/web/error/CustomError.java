package com.cev.finalproyect.proyectservices.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CustomError extends RuntimeException{

	
	private static final long serialVersionUID = 1L;



	public CustomError(String message) {
		super(message);
		
	}

}
