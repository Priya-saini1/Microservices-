package com.example.RatingService.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RatingService.entity.Rating;
import com.example.RatingService.repository.RatingRepository;
import com.example.RatingService.service.UserRating;

@Service
public class RatingServiceImpl implements UserRating{
	
	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public Rating createRating(Rating r) {
		return ratingRepository.save(r);
	}

	@Override
	public List<Rating> getAllRating() {
		// TODO Auto-generated method stub
		return ratingRepository.findAll();
	}

	@Override
	public List<Rating> getRatingById(String userId) {
		// TODO Auto-generated method stub
		return ratingRepository.findByUserId(userId);
	}

	@Override
	public List<Rating> getRatingByHotelId(String hotelId) {
		// TODO Auto-generated method stub
		return ratingRepository.findByHotelId(hotelId);
	}

}
