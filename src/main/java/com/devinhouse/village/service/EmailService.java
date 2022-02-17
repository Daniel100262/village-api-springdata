package com.devinhouse.village.service;

import org.springframework.mail.SimpleMailMessage;

import com.devinhouse.village.model.dao.User;

public interface EmailService {
	
	void sendEmail(SimpleMailMessage message);
	
	void sendNewPassword(User user, String newPassword);

}
