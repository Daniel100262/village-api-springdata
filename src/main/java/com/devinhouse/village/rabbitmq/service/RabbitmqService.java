package com.devinhouse.village.rabbitmq.service;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devinhouse.village.model.transport.VillageReportDTO;
import com.devinhouse.village.repositories.UserCredentialRepository;
import com.devinhouse.village.service.ResidentService;
import com.devinhouse.village.util.ExportPDF;

@Service
public class RabbitmqService {
	
	@Autowired
	private EmailServicePDF emailServicePDF;
	
	@Autowired
	ResidentService residentService;
	
	@Autowired
	UserCredentialRepository userCredentialRepository;
	
	private RabbitTemplate rabbitTemplate;
	
	//public RabbitmqService(EmailServicePDF emailServicePDF) {
	//	this.emailServicePDF = emailServicePDF;
	//}
	
	public RabbitmqService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
	
	public boolean sendMessage(String routingKey, Object messageBody) {
        try{
           // logger.debug("Sending message: ".concat(messageBody.toString()).concat(", to ").concat(routingKey));
            rabbitTemplate.convertAndSend(routingKey, messageBody);
            return true;
        } catch (AmqpException e) {
            return false;
        }
    }
	
	public void sendReportByEmail(String email, VillageReportDTO reportDTO) throws Exception {
		//UserCredential user = userCredentialRepository.getUserByEmail(email);
		
		//if(user == null) {
		//	throw new Exception("E-mail não encontrado!");
		//}
		
		ExportPDF exporter = new ExportPDF(reportDTO);
		byte[] report = exporter.export();
		
		emailServicePDF.sendMailWithAttachment(email, "Relatório da vila", "Segue em anexo, o relatório.", report);
		
		//emailService.sendPDFReport(user, report);
		
	}

}
