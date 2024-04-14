package com.cev.finalproyect.proyectservices.auth;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AuthResponse {
    private String token;
    private Long id;
    private String email;
    private String name;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    private AuthResponse() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public Long getId() { return id; }
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        private String token;
        private Long id;
        private String email;
        private String name;
        private String lastName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        private Date dateOfBirth;

        public AuthResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AuthResponseBuilder email(String email) {
            this.email = email;
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
            authResponse.setEmail(this.email);
            authResponse.setName(this.name);
            authResponse.setLastName(this.lastName);
            authResponse.setDateOfBirth(this.dateOfBirth);
            return authResponse;
        }
    }
}
