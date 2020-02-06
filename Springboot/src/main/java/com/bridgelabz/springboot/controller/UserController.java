package com.bridgelabz.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.bridgelabz.springboot.dto.RegisterDto;
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
	public ResponseEntity<Response> addUser(@RequestBody RegisterDto registrationDto) {
		System.out.println("my data" + registrationDto.toString());
		return new ResponseEntity<Response>(service.saveUser(registrationDto), HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginDto loginDto) {
		return new ResponseEntity<Response>(service.login(loginDto), HttpStatus.OK);
	}

	@PostMapping("/forgotPassword")
	public ResponseEntity<Response> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {
		return new ResponseEntity<Response>(service.forgotPassword(forgotPasswordDto), HttpStatus.OK);
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<Response> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
		return new ResponseEntity<Response>(service.resetPassword(resetPasswordDto), HttpStatus.OK);
	}

	@PostMapping("/isVerify")
	public ResponseEntity<Response> isVerifiy(@RequestParam String token) {
		return new ResponseEntity<Response>(service.isVerified(token), HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<List<User>> list() {
		return new ResponseEntity<List<User>>(service.getAllUsers(), HttpStatus.OK);

	}

	@GetMapping("/list/{id}")
	public User getById(@PathVariable int id) {
		return service.getByIb(id);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable(value = "id") int id) {
		service.deleteUser(id);
		return "Deleted " + id;
	}
}
