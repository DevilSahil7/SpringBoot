package com.bridgelabz.springboot.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.springboot.dto.ForgotPasswordDto;
import com.bridgelabz.springboot.dto.LoginDto;
import com.bridgelabz.springboot.dto.RegisterDto;
import com.bridgelabz.springboot.dto.ResetPasswordDto;
import com.bridgelabz.springboot.model.User;
import com.bridgelabz.springboot.repository.UserRepository;
import com.bridgelabz.springboot.utility.Jms;
import com.bridgelabz.springboot.utility.Jwt;
import com.bridgelabz.springboot.utility.Response;

@Service
@PropertySource("classpath:message.properties")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private Jwt jwt;

	@Autowired
	private Jms jms;

	@Autowired
	private Environment environment;

	@Autowired
	private BCryptPasswordEncoder bryBCryptPasswordEncoder;

	@Autowired
	ModelMapper modelMapper;

	public List<User> getAllUsers() {
		List<User> user = repository.findAll();
		if (user != null) {
			System.out.println(user);
			return user;
		}
		return null;
	}

	@Override
	public User getByIb(int id) {

		return repository.findById(id).get();
	}

	@Override
	public void deleteUser(int id) {
		repository.deleteById(id);
	}

	@Override
	public Response saveUser(RegisterDto registrationDto) {
		User user = modelMapper.map(registrationDto, User.class);
		System.out.println(user.getAddress());
		boolean register = false;
		User userExist = repository.findByEmail(registrationDto.getEmail());
		if (userExist != null) {
			System.out.println(environment.getProperty("USER_PRESENT"));
		} else {
			if (registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
				String token = jwt.createToken(registrationDto.getEmail());
				jms.sendMail(registrationDto.getEmail(), token);
				bryBCryptPasswordEncoder.encode(registrationDto.getPassword());
				repository.save(user);
				register = true;
			}
		}
		if (!register) {
			return new Response(environment.getProperty("SERVER_CODE_ERROR"),
					environment.getProperty("INVALID_CREDENTIALS"));
		} else {
			return new Response(environment.getProperty("SERVER_CODE_SUCCESS"),
					environment.getProperty("USER_CREATED"));
		}

	}

	@Override
	public Response login(LoginDto loginDto) {
		User user = repository.findByEmail(loginDto.getEmail());
		boolean login = false;
		if (user == null) {
			System.out.println("User not found!");
		} else {
			if (user.isVerify() == true) {
				if (user.getPassword().equals(loginDto.getPassword())) {
					login = true;
				}
			}

		}
		if (login != true)

		{
			return new Response(environment.getProperty("SERVER_CODE_ERROR"),
					environment.getProperty("INVALID_PASSWORD"));

		} else {
			return new Response(environment.getProperty("SERVER_CODE_SUCCESS"),
					environment.getProperty("LOGIN_SUCCESS"));
		}
	}

	@Override
	public Response forgotPassword(ForgotPasswordDto forgotPasswordDto) {
		User userForget = repository.findByEmail(forgotPasswordDto.getEmail());
		if (userForget != null) {
			String token = jwt.createToken(forgotPasswordDto.getEmail());
			jms.sendMail(forgotPasswordDto.getEmail(), token);
			return new Response(environment.getProperty("SERVER_CODE_SUCCESS"),
					environment.getProperty("FORGOT_PASSWORD"));
		} else {
			return new Response(environment.getProperty("SERVER_CODE_ERROR"),
					environment.getProperty("INVALID_CREDENTIALS"));
		}
	}

	public Response resetPassword(ResetPasswordDto resetPasswordDto) {
		User user = modelMapper.map(resetPasswordDto, User.class);
		User userReset = repository.findByEmail(resetPasswordDto.getEmail());
		if (userReset != null) {
			if (resetPasswordDto.getPassword().equals(resetPasswordDto.getConfirmPassword())) {
				repository.save(user);
				return new Response(environment.getProperty("SERVER_CODE_SUCCESS"),
						environment.getProperty("RESET_SUCCESS"));
			}
		}
		return new Response(environment.getProperty("SERVER_CODE_ERROR"),
				environment.getProperty("INVALID_CREDENTIALS"));
	}

	public Response isVerified(String token) {
		String email = jwt.getUserToken(token);
		User user = repository.findByEmail(email);
		if (user != null) {
			user.setVerify(true);
			repository.save(user);
			return new Response(environment.getProperty("SERVER_CODE_SUCCESS"),
					environment.getProperty("TOKEN_SUCCESS"));
		} else
			return new Response(environment.getProperty("SERVER_CODE_ERROR"), environment.getProperty("TOKEN_ERROR"));
	}

}
