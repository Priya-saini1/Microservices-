package com.example.HotelService.service;

import java.util.List;

import com.example.HotelService.entity.Hotel;

public interface HotelService {

	public Hotel createHotel(Hotel hotel);
	
	public List<Hotel> getAllHote();
	
	public Hotel getSingleHotel(String hotelId);
	
}
