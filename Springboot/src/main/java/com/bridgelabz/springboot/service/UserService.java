package com.bridgelabz.springboot.service;

import java.util.List;

import com.bridgelabz.springboot.dto.ForgotPasswordDto;
import com.bridgelabz.springboot.dto.LoginDto;
import com.bridgelabz.springboot.dto.RegisterDto;
import com.bridgelabz.springboot.dto.ResetPasswordDto;
import com.bridgelabz.springboot.model.User;
import com.bridgelabz.springboot.utility.Response;

public interface UserService {

	public List<User> getAllUsers();

	public User getByIb(int id);

	//public void saveUser(User user);

	public void deleteUser(int Id);
	
	//User login(LoginDto loginDto);

	public Response saveUser(RegisterDto registrationDto);

	public Response login(LoginDto loginDto);
	
	public Response forgotPassword(ForgotPasswordDto forgotPasswordDto);
	
	public Response resetPassword(ResetPasswordDto resetPasswordDto);
	
	public Response isVerified(String token);

//	void saveUser(User registrationDto);
}
