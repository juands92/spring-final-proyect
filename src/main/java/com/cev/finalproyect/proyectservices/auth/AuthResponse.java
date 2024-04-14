package com.cev.finalproyect.proyectservices.auth;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class AuthResponse {
	private UUID id;
	private String token;
    private String username;
    private String name;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    private AuthResponse() {}
    
    public UUID getId() {
 		return id;
 	}

 	public void setId(UUID id) {
 		this.id = id;
 	}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Date getDateOfBirth() {return dateOfBirth;}
    public void setDateOfBirth(Date dateOfBirth) {this.dateOfBirth = dateOfBirth;}

    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

    public static class AuthResponseBuilder {
    	private UUID id;
        private String token;
        private String username;
        private String name;
        private String lastName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        private Date dateOfBirth;
        
        public AuthResponseBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public AuthResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthResponseBuilder username(String username) {
            this.username = username;
            return this;
        }

        public AuthResponseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AuthResponseBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public AuthResponseBuilder dateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public AuthResponse build() {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setId(this.id);
            authResponse.setToken(this.token);
            authResponse.setUsername(this.username);
            authResponse.setName(this.name);
            authResponse.setLastName(this.lastName);
            authResponse.setDateOfBirth(this.dateOfBirth);
            return authResponse;
        }
    }
}
