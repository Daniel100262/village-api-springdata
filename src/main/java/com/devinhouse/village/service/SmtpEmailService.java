package com.devinhouse.village.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.devinhouse.village.model.dao.UserCredential;
import com.lowagie.text.pdf.PdfDocument;

public class SmtpEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

	private MailSender mailSender;

	public SmtpEmailService(String sender, MailSender mailSender) {
		super(sender);
		this.mailSender = mailSender;
	}

	@Override
	public void sendEmail(SimpleMailMessage message) {
		LOG.info("Enviando email...");
		mailSender.send(message);
		LOG.info("Email enviado");
	}

	@Override
	public void sendPDFReport(UserCredential user, PdfDocument report) {
		// TODO Auto-generated method stub
		
	}

}
