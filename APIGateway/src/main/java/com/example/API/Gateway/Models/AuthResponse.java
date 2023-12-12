package com.example.API.Gateway.Models;

import java.util.Collection;

import lombok.Data;

@Data
public class AuthResponse {

	private String userId;
	private String accessToken;
	private String refreshToken;
	private long expireAt;
	private Collection<String> authorities;
	
	
}
