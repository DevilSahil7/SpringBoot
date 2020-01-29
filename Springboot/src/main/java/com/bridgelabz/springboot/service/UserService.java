package com.bridgelabz.springboot.service;

import java.util.List;

import com.bridgelabz.springboot.dto.ForgotPasswordDto;
import com.bridgelabz.springboot.dto.LoginDto;
import com.bridgelabz.springboot.dto.RegistrationDto;
import com.bridgelabz.springboot.dto.ResetPasswordDto;
import com.bridgelabz.springboot.model.User;
import com.bridgelabz.springboot.utility.Response;

public interface UserService {

	public List<User> getAllUsers();

	public User getByIb(int id);

	//public void saveUser(User user);

	public void deleteUser(int Id);
	
	//User login(LoginDto loginDto);

	Response saveUser(RegistrationDto registrationDto);

	Response login(LoginDto loginDto, String token);
	
	Response forgotPassword(ForgotPasswordDto forgotPasswordDto);
	
	Response resetPassword(ResetPasswordDto resetPasswordDto);

//	void saveUser(User registrationDto);
}
