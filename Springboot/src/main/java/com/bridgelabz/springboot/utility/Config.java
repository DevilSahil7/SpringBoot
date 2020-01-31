package com.bridgelabz.springboot.utility;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Config {

	@Bean
	public BCryptPasswordEncoder bcyBCryptPasswordEncoder() 
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	

}
