package com.cev.finalproyect.proyectservices.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	String email;
    String password;
	private boolean rememberUser;
	public boolean isRememberUser() {
		return rememberUser;
	}

	public void setRememberUser(boolean rememberUser) {
		this.rememberUser = rememberUser;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}