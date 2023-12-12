package com.example.RatingService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RatingService.entity.Rating;
import com.example.RatingService.service.UserRating;

@RestController
@RequestMapping("/ratings")
public class RatingController {

	@Autowired
	private UserRating userRating;
	
	@PreAuthorize("hasAuthority('Admin')")
	@PostMapping("/createRating")
	public ResponseEntity<Rating> createRating(@RequestBody Rating rating){
		return ResponseEntity.status(HttpStatus.CREATED).body(userRating.createRating(rating));
	}
	
	
	@GetMapping("/getRating")
	public ResponseEntity<List<Rating>> getAllRating(){
		return ResponseEntity.status(HttpStatus.OK).body(userRating.getAllRating());
	}
	
	@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
	@GetMapping("/getRating/{userId}")
	public ResponseEntity<List<Rating>> getRatingByUserId(@PathVariable("userId") String UserId){
		return ResponseEntity.status(HttpStatus.OK).body(userRating.getRatingById(UserId));
	}
	
	@GetMapping("/hotels/{hotelId}")
	public ResponseEntity<List<Rating>> getRatingByHotelId(@PathVariable("hotelId") String hotelId){
		return ResponseEntity.status(HttpStatus.OK).body(userRating.getRatingByHotelId(hotelId));
	}
}
