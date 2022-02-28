package com.devinhouse.village.rabbitmq.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devinhouse.village.model.transport.VillageReportDTO;
import com.devinhouse.village.rabbitmq.Sender;
import com.devinhouse.village.service.ResidentService;
import com.lowagie.text.DocumentException;

@RestController
@RequestMapping("/pdf/report")
public class RabbitRest {

    @Autowired
    private Sender queueSender;
    
	@Autowired
	private ResidentService residentService;
    
    @PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/generate")
	public void exportToPDF() throws DocumentException, IOException {

		VillageReportDTO report = residentService.genereteReport();
		
		queueSender.send(report);
		
		
		
	}
    
}
