package com.foodorder.Online_Food_Order.controller;



import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodorder.Online_Food_Order.entity.User;
import com.foodorder.Online_Food_Order.repository.UserRepository;
import com.foodorder.Online_Food_Order.service.UserService;

import jakarta.validation.Valid;


	@CrossOrigin(origins = "http://localhost:4200")
	@RestController // is controller which provides different end points to access the services

	@RequestMapping("/api/users")
	public class UserController {

		@Autowired
		private UserService userService;
		
		@Autowired
		private UserRepository uRepository;

		
		 public UserController(UserService userService) 
		 { 
			 super();
		  this.userService = userService;
		  }
	 	 

	//Register
		//http://localhost:8084/register
		@PostMapping("/register")
		public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {

			return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.CREATED);
		}

	//Login
		@PostMapping("/login")
		public ResponseEntity<User> loginUser(@RequestBody User user) {

			return new ResponseEntity<User>(userService.loginUser(user), HttpStatus.OK);

		}

	//Update Customer	
		@PutMapping("user/{id}")
		public ResponseEntity<User> updateUser(@PathVariable("id") long userId, @RequestBody User user) {
			return new ResponseEntity<User>(userService.updateUser(user, userId), HttpStatus.OK);
		}

	//Get All Customer	
		@GetMapping()
		public List<User> getAllUser() {
			return userService.getAllUser();
		}

		// get customer by email
		@PostMapping("/forgotpassword")
		public User getUserByEmail(@RequestBody User user) {
			return uRepository.findByEmailId(user.getEmailID()).get();
		}

		// get customer by id
		@GetMapping("user/{id}")
		public ResponseEntity<User> getUserById(@PathVariable("id") long userId) {
			return new ResponseEntity<User>(userService.getUserById(userId), HttpStatus.OK);
		}

	//Delete Customer	
		@DeleteMapping("user/{id}")
		public ResponseEntity<Boolean> deleteUser(@PathVariable("id") long userId) {
			userService.deleteUser(userId);
			boolean flag = true;
			return new ResponseEntity<Boolean>(flag, HttpStatus.OK);
		}
		
		@PostMapping("/{uid}/{newpassword}")
		public User changeUserPassword(@PathVariable("uid") long uid,@PathVariable("newpassword") String newpassword) {
			//return customerService.getCustomerByEmail(customer);
			User u=userService.getUserById(uid);
			u.setPassword(newpassword);
			userService.updateUser(u, uid);
			return u;
		}
	}

