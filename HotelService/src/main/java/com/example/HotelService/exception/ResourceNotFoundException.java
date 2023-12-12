package com.example.HotelService.exception;

public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException() {
		super("Resource not found Exception");
	}
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
	

}