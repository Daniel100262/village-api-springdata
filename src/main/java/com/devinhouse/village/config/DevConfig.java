package com.devinhouse.village.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;

import com.devinhouse.village.service.EmailService;
import com.devinhouse.village.service.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

	private String sender;

	private MailSender mailSender;

	public DevConfig(@Value("${default.sender}") String sender, 
			MailSender mailSender) {
		this.sender = sender;
		this.mailSender = mailSender;
	}

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService(sender, mailSender);
	}
}
