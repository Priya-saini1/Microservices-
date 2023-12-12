package com.example.RatingService.service;

import java.util.List;

import com.example.RatingService.entity.Rating;

public interface UserRating {

	public Rating createRating(Rating r);
	
	public List<Rating> getAllRating();
	
	public List<Rating> getRatingById(String userId);
	
	public List<Rating> getRatingByHotelId(String hotelId);
	
}
