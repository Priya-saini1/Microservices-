package com.example.UserService.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.UserService.Dto.userRequestDto;
import com.example.UserService.entity.User;

public interface UserService {

	public User saveUser(User user);
	
	public List<User> getAllUser();
	
	public User getUserById(String userId);
	
	public void deleteUserById(String userId);
	
	public User updateUserByID(String userId,userRequestDto U);
}
