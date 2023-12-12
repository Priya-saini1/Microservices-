package com.example.HotelService.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HotelService.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, String>{

}
