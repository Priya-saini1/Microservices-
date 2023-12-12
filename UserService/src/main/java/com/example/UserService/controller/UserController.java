package com.example.UserService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserService.Dto.userRequestDto;
import com.example.UserService.entity.User;
import com.example.UserService.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
@RefreshScope
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/CreateUser")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
	}

//	 int retryCount=1;
	@GetMapping("/getUser/{id}")
//	@CircuitBreaker(name = "ratingHotelCircuitBreaker", fallbackMethod = "ratingHotelFallBackMethod")
//	@Retry(name="ratingHotelRetry",fallbackMethod = "ratingHotelFallBackMethod")
	@RateLimiter(name="ratingHotelRateLimiter",fallbackMethod = "ratingHotelFallBackMethod")
	public ResponseEntity<User> getSingleUser(@PathVariable("id") String userId) {
		/*
		 * System.out.println("Retry................"+retryCount); retryCount++;
		 */
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
	}

	/*
	 * creating fallback method for circuitBreak and make sure the return type and
	 * no of arguments must be same as above circuit breaker method
	 */
	public ResponseEntity<User> ratingHotelFallBackMethod(String userId, Exception ex) {
		System.out.println("Fallback is executed because service is down!!!!");
		ex.printStackTrace();
		User user  = User.builder().name("dummy User")
		.email("dummyUser@gmail.com")
		.about("This user is created dummy because some service is down!!! ")
		.userId("12345")
		.build();
		return new ResponseEntity<>(user,HttpStatus.OK);
	}

	@GetMapping("/getUser")
	public ResponseEntity<List<User>> getAllUser() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());

	}

	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") String userId) {
		userService.deleteUserById(userId);
		return ResponseEntity.status(HttpStatus.OK).body("User Deleted Successfully !!!!" + userId);
	}

	@PutMapping("/updateUser/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") String userId, @RequestBody userRequestDto userDto) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserByID(userId, userDto));
	}
}
