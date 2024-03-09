package com.cev.finalproyect.proyectservices.auth;

public class AuthResponse {
	
    private String token;

  
    private AuthResponse() {}


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
        private String token;

        public AuthResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthResponse build() {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setToken(this.token);
            return authResponse;
        }
    }
}