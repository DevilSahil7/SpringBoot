package com.bridgelabz.springboot.service;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.springboot.dto.ForgotPasswordDto;
import com.bridgelabz.springboot.dto.LoginDto;
import com.bridgelabz.springboot.dto.RegistrationDto;
import com.bridgelabz.springboot.dto.ResetPasswordDto;
import com.bridgelabz.springboot.model.User;
import com.bridgelabz.springboot.repository.UserRepository;
import com.bridgelabz.springboot.utility.Jms;
import com.bridgelabz.springboot.utility.Jwt;
import com.bridgelabz.springboot.utility.Response;

@Service
@Transactional
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

	private ModelMapper modelMapper;

	@Override
	public List<User> getAllUsers() {
		List<User>  user = repository.findAll();
		if(user != null)
		{
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
	public Response saveUser(RegistrationDto registrationDto) {
		User user = modelMapper.map(registrationDto, User.class);
		User userExist = repository.findByEmail(registrationDto.getEmail());
		if (userExist != null) {
			System.out.println(environment.getProperty("USER_PRESENT"));
		} else {
			if (registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
				String token = jwt.createToken(registrationDto.getEmail());
				jms.sendMail(registrationDto.getEmail(), token);
				bryBCryptPasswordEncoder.encode(registrationDto.getPassword());
				return new Response(environment.getProperty("SERVIER_CODE_SUCCESS"),
						environment.getProperty("USER_CREATED"));
			}
		}

		repository.save(user);
		return new Response(environment.getProperty("SERVIER_CODE_ERROR"),
				environment.getProperty("INVALID_CREDENTIALS"));
	}

	@Override
	public Response login(LoginDto loginDto, String token) {
		String email = jwt.getUserToken(token);
		User user = modelMapper.map(loginDto, User.class);
		User userLogin = repository.findByEmail(user.getEmail());
		if (userLogin == null) {
			System.out.println("User not found!");
		} else {
			if (isVerify(email)) {
				if (userLogin.getPassword().equals(loginDto.getPassword())) {
					repository.save(user);
					return new Response(environment.getProperty("SERVER_CODE_SUCCESS"),
							environment.getProperty("LOGIN_SUCCESS"));
				}
			}
			return new Response(environment.getProperty("SERVER_CODE_ERROR"), environment.getProperty("TOKEN_ERROR"));
		}
		return new Response(environment.getProperty("SERVER_CODE_ERROR"),
				environment.getProperty("INVALID_CREDENTIALS"));
	}

	public Response forgotPassword(ForgotPasswordDto forgotPasswordDto) {
		User user = modelMapper.map(forgotPasswordDto, User.class);
		User userForget = repository.findByEmail(forgotPasswordDto.getEmail());
		if (userForget != null) {
			String token = jwt.createToken(forgotPasswordDto.getEmail());
			jms.sendMail(forgotPasswordDto.getEmail(), token);
			return new Response(environment.getProperty("SERVER_CODE_SUCCESS"),
					environment.getProperty("FORGOT_PASSWORD"));
		}
		return new Response(environment.getProperty("SERVER_CODE_ERROR"),
				environment.getProperty("INVALID_CREDENTIALS"));
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

	public boolean isVerify(String token) {
		User verify = repository.findByEmail(token);
		if (verify != null) {
			return true;
		} else
			return false;
	}

//	@Override
//	public void saveUser(User registrationDto) {
//		//User user = modelMapper.map(registrationDto, User.class);
//		repository.save(registrationDto);
//	}
//	@Override
//	public void saveUser(RegistrationDto registrationDto) {
//		User user = modelMapper.map(registrationDto, User.class);
//		repository.save(user);
//
//	}

}
