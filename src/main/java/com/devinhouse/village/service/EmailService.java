package com.devinhouse.village.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.devinhouse.village.model.transport.UserDTO;

public interface EmailService {
	
	void sendEmail(SimpleMailMessage message);
	
	void sendNewPassword(UserDTO user, String newPassword);

}
