package com.bridgelabz.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.springboot.dto.ForgotPasswordDto;
import com.bridgelabz.springboot.dto.LoginDto;
import com.bridgelabz.springboot.dto.RegistrationDto;
import com.bridgelabz.springboot.dto.ResetPasswordDto;
import com.bridgelabz.springboot.model.User;
import com.bridgelabz.springboot.repository.UserRepository;
import com.bridgelabz.springboot.service.UserService;
import com.bridgelabz.springboot.utility.Response;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserService service;

	@Autowired
	UserRepository repository;

	@PostMapping("/addUser")
	public ResponseEntity<Response> addUser(@RequestBody RegistrationDto registrationDto) {
		return new ResponseEntity<Response>(service.saveUser(registrationDto), HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginDto loginDto, @RequestParam String token) {
		return new ResponseEntity<Response>(service.login(loginDto, token), HttpStatus.OK);
	}

	@PostMapping("/forgotPassword")
	public ResponseEntity<Response> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {
		return new ResponseEntity<Response>(service.forgotPassword(forgotPasswordDto), HttpStatus.OK);
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<Response> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
		return new ResponseEntity<Response>(service.resetPassword(resetPasswordDto), HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<List> list() {
		return new ResponseEntity<List> (service.getAllUsers(),HttpStatus.OK);

	}

	@GetMapping("/list/{id}")
	public User getById(@PathVariable int id) {
		return service.getByIb(id);
	}

//
	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable(value = "id") int id) {
		service.deleteUser(id);
		return "Deleted " + id;
	}

//	@PostMapping("/save")
//	public RegistrationDto save(@RequestBody RegistrationDto user) {
//		User dbuser = repository.findByEmail(user.getEmail());
//		user.toString();
//		if(dbuser != null) {
//			System.out.println("already exist");
//		}else {
//		
//		service.saveUser(user);
//		}
//		return user;
//	}
//	
	//
//	@PostMapping("/login")
//	public void login(@RequestBody LoginDto loginDto) {
//		List<User> users = repository.findAll();
//		boolean find = false;
//		for (User user : users) {
//			if (user.getUserName().equals(loginDto.getUserName())
//					&& user.getPassword().equals(loginDto.getPassword())) {
//				find = true;
//				System.out.println("login success");
//				
//			}
//		}
//		if (!find) {
//			System.out.println("login failed");
//		}
//
//	}
}
