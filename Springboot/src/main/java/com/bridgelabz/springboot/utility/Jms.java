package com.bridgelabz.springboot.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Jms {

	@Autowired
	private JavaMailSender javaMailsender;

	public void sendMail(String email, String token) 
	{
		System.out.println("email" + email);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("sahilkudake7@gmail.com");
		mail.setTo(email);
		mail.setText(token);
		mail.setSubject("Verification Token");
		System.out.println(mail.getFrom());
		System.out.println(mail.getSubject());
		System.out.println(mail.getTo());

		javaMailsender.send(mail);
	}
}
