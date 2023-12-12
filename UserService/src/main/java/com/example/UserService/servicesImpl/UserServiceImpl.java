package com.example.UserService.servicesImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.UserService.Dto.userRequestDto;
import com.example.UserService.entity.Hotel;
import com.example.UserService.entity.Ratings;
import com.example.UserService.entity.User;
import com.example.UserService.exception.ResourceNotFoundException;
import com.example.UserService.externalService.HotelService;
import com.example.UserService.repository.UserRepository;
import com.example.UserService.services.UserService;
import com.netflix.discovery.converters.Auto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	
	
	@Override
	public User saveUser(User user) {
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		List<User> user  = userRepository.findAll();
		user.stream().forEach((i)->{
			Ratings[] getAllRating = restTemplate.getForObject("http://RATING-SERVICE/ratings/getRating/"+i.getUserId(), Ratings[].class);
			List<Ratings> ratings = Arrays.stream(getAllRating).toList();
			ratings.stream().forEach(rating->{
				Hotel hotel = restTemplate.getForObject("http://HOTEL-SERVICE/hotels/getHotel/"+rating.getHotelId(), Hotel.class);
				rating.setHotel(hotel);
			});
			i.setRatings(ratings);
		});
		return user;
	}

	@Override
	public User getUserById(String userId) {
		User user =  userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found on the given id "+userId));
		// fetch the rating list of particular users from RATING SERVICE
		Ratings[] ratingOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/getRating/"+userId,Ratings[].class);
	
		List<Ratings> ratings = Arrays.stream(ratingOfUser).toList();
	
		List<Ratings> ratingList = ratings.stream().map(rating->{
			//API call to hotel service to get the hotel 
//			Hotel hotel = restTemplate.getForObject("http://HOTEL-SERVICE/hotels/getHotel/"+rating.getHotelId(), Hotel.class);
			
			Hotel hotel = hotelService.getHotel(rating.getHotelId()) ;
			//set the hotel to rating
			rating.setHotel(hotel);
			//return the rating 
			return rating;
		}).collect(Collectors.toList());
	
		user.setRatings(ratingList);
		return user;
	}

	@Override
	public void deleteUserById(String userId) {
		// TODO Auto-generated method stub
		 userRepository.deleteById(userId);
	}

	@Override
	public User updateUserByID(String userId,userRequestDto u) {
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			User updateUser = user.get();
			updateUser.setEmail(u.getEmail());
			updateUser.setAbout(u.getAbout());
			updateUser.setName(u.getName());
			return userRepository.save(updateUser);
		}
		else {
			return user.get();
		}
		
	}

}
