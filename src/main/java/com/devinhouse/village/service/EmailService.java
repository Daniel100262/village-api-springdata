package com.devinhouse.village.service;

import org.springframework.mail.SimpleMailMessage;

import com.devinhouse.village.model.dao.UserCredential;
import com.lowagie.text.pdf.PdfDocument;

public interface EmailService {
	
	void sendEmail(SimpleMailMessage message);
	
	void sendNewPassword(UserCredential user, String newPassword);
	void sendPDFReport(UserCredential user, PdfDocument report);

}
