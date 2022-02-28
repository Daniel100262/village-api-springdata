package com.devinhouse.village.service;

import java.util.Date;

import org.springframework.mail.SimpleMailMessage;

import com.devinhouse.village.model.dao.UserCredential;


public abstract class AbstractEmailService implements EmailService {

	private String sender;

	public AbstractEmailService(String sender) {
		this.sender = sender;
	}

	@Override
	public void sendNewPassword(UserCredential user, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(user, newPass);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(UserCredential user, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(user.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		
		return sm;
	}

}
