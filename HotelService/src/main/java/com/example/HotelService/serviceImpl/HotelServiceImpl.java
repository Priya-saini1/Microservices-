package com.example.HotelService.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HotelService.entity.Hotel;
import com.example.HotelService.exception.ResourceNotFoundException;
import com.example.HotelService.respository.HotelRepository;
import com.example.HotelService.service.HotelService;

@Service
public class HotelServiceImpl implements HotelService{

	@Autowired
	private HotelRepository hotelRepository;
	
	@Override
	public Hotel createHotel(Hotel hotel) {
		String randomId = UUID.randomUUID().toString();
		hotel.setHotelId(randomId);
		return hotelRepository.save(hotel);
	}

	@Override
	public List<Hotel> getAllHote() {
		// TODO Auto-generated method stub
		return hotelRepository.findAll();
	}

	@Override
	public Hotel getSingleHotel(String hotelId) {
		// TODO Auto-generated method stub
		return hotelRepository.findById(hotelId).orElseThrow(()->new ResourceNotFoundException("Hotel Not Found Exception"+hotelId));
	}

}
