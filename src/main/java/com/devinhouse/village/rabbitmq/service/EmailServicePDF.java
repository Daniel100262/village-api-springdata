package com.devinhouse.village.rabbitmq.service;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailServicePDF {
	@Autowired
	private JavaMailSender mailSender;


	public void sendMailWithAttachment(String to, String subject, String body, byte[] reportToAttach) {
		MimeMessage message = mailSender.createMimeMessage();
	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setFrom("villagedevinhouse@gmail.com");
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(body);
	        helper.addAttachment("report.pdf", new ByteArrayResource(reportToAttach));
	        mailSender.send(message);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
		
	}
	
	
}