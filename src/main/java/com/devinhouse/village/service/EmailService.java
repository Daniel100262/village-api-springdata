package com.devinhouse.village.service;

import org.springframework.mail.SimpleMailMessage;

import com.devinhouse.village.model.UserCredential;

public interface EmailService {
	
	void sendEmail(SimpleMailMessage message);
	
	void sendNewPassword(UserCredential user, String newPassword);

}
