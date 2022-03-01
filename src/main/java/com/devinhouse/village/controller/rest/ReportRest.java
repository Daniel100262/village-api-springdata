package com.devinhouse.village.controller.rest;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devinhouse.village.model.transport.VillageReportDTO;
import com.devinhouse.village.rabbitmq.Sender;
import com.devinhouse.village.service.ResidentService;
import com.devinhouse.village.util.ExportPDF;
import com.lowagie.text.DocumentException;

@RestController
@RequestMapping("/report")
public class ReportRest {

	@Autowired
	private ResidentService residentService;

	@Autowired
    private Sender queueSender;
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/generate")
	public VillageReportDTO getFinancialReport() throws SQLException {
		return residentService.genereteReport();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/generate/pdf")
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=village-report.pdf";
		response.setHeader(headerKey, headerValue);

		VillageReportDTO report = residentService.genereteReport();

		ExportPDF exporter = new ExportPDF(report);
		exporter.export(response);
	}
	    
    @PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/generate/pdf/email")
	public void exportToPDF(@RequestParam("email") String emailDestination) throws DocumentException, IOException {
		VillageReportDTO report = residentService.genereteReport(emailDestination);
		queueSender.send(report);
	}

}
