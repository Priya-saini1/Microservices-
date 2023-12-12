package com.example.HotelService.controller;

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

import com.example.HotelService.entity.Hotel;
import com.example.HotelService.service.HotelService;

@RestController
@RequestMapping("/hotels")
public class hotelController {

	@Autowired
	private HotelService hotelService;
	
	@PreAuthorize("hasAuthority('Admin')")
	@PostMapping("/createHotel")
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
		return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.createHotel(hotel));
	}
	
	@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
	@GetMapping("/getHotel")
	public ResponseEntity<List<Hotel>> getAllHotel(){
		return ResponseEntity.status(HttpStatus.OK).body(hotelService.getAllHote());
	}
	
	@PreAuthorize("hasAuthority('SCOPE_internal')")
	@GetMapping("/getHotel/{id}")
	public ResponseEntity<Hotel> getSingleHotel(@PathVariable("id")String hotelId){
		return ResponseEntity.status(HttpStatus.OK).body(hotelService.getSingleHotel(hotelId));
	}
}
