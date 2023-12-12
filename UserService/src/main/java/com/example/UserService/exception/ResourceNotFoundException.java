package com.example.UserService.exception;

public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException() {
		super("Resource not found Exception");
	}
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
	

}
