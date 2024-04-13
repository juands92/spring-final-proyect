package com.cev.finalproyect.proyectservices.auth;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AuthResponse {
    private String token;
    private String username;
    private String name;
    private String lastName;
    private String error;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    private AuthResponse() {}

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

    public AuthResponse(String token, String username, String name, String lastName, String error, Date dateOfBirth) {
        this.token = token;
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.error = error;
        this.dateOfBirth = dateOfBirth;
    }

    public static class AuthResponseBuilder {
        private String token;
        private String username;
        private String name;
        private String lastName;
        private String error;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        private Date dateOfBirth;

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

        public AuthResponseBuilder error(String error) {
            this.error = error;
            return this;
        }

        public AuthResponseBuilder dateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public AuthResponse build() {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setToken(this.token);
            authResponse.setUsername(this.username);
            authResponse.setName(this.name);
            authResponse.setLastName(this.lastName);
            authResponse.setError(this.error);
            authResponse.setDateOfBirth(this.dateOfBirth);
            return authResponse;
        }
    }
}
