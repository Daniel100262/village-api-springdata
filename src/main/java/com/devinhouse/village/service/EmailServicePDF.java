package com.devinhouse.village.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServicePDF {
	
	private JavaMailSender mailSender;

	@Autowired
	public EmailServicePDF(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}



	public void sendMailWithAttachment(String emailDestination, String subject, String body, byte[] reportToAttach) {
		MimeMessage message = mailSender.createMimeMessage();
	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setFrom("villagedevinhouse@gmail.com");
	        helper.setTo(emailDestination);
	        helper.setSubject(subject);
	        helper.setText(body);
	        helper.addAttachment("report.pdf", new ByteArrayResource(reportToAttach));
	        mailSender.send(message);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
		
	}
	
	
}