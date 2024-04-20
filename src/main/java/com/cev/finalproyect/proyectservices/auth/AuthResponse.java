package com.cev.finalproyect.proyectservices.auth;
import java.util.UUID;

public class AuthResponse {
	private UUID id;
	private String token;

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

    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

    public static class AuthResponseBuilder {
    	private UUID id;
        private String token;
        
        public AuthResponseBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public AuthResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthResponse build() {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setId(this.id);
            authResponse.setToken(this.token);
            return authResponse;
        }
    }
}
