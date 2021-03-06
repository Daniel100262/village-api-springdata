package com.devinhouse.village.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devinhouse.village.exception.InvalidDestinationException;
import com.devinhouse.village.exception.NullDTOException;
import com.devinhouse.village.model.transport.VillageReportDTO;
import com.devinhouse.village.repositories.UserCredentialRepository;
import com.devinhouse.village.util.ExportPDF;

@Service
public class RabbitmqService {
	
	private EmailServicePDF emailServicePDF;
	
	ResidentService residentService;
	
	UserCredentialRepository userCredentialRepository;
	
	
	@Autowired
	public RabbitmqService(EmailServicePDF emailServicePDF, ResidentService residentService,
			UserCredentialRepository userCredentialRepository) {
		super();
		this.emailServicePDF = emailServicePDF;
		this.residentService = residentService;
		this.userCredentialRepository = userCredentialRepository;
	}



	public void sendReportByEmail(String emailDestination, VillageReportDTO reportDTO) throws Exception {
		
		if(emailDestination.isEmpty()) {
			throw new InvalidDestinationException(emailDestination);
		}
		
		if(reportDTO.equals(null)) {
			throw new NullDTOException(reportDTO.getClass().toString());
		}
		
		ExportPDF exporter = new ExportPDF(reportDTO);
		byte[] report = exporter.export();
		
		emailServicePDF.sendMailWithAttachment(emailDestination, "Relatório da vila", "Segue em anexo, o relatório.", report);
	}

}
