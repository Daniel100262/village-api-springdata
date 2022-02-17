package com.devinhouse.village.controller.rest;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devinhouse.village.model.transport.VillageReportDTO;
import com.devinhouse.village.service.ResidentService;

@RestController
@RequestMapping("/report")
public class ReportRest {
	
	@Autowired
	private ResidentService residentService;

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/generate")
	public VillageReportDTO getFinancialReport() throws SQLException{
		return residentService.genereteReport();
	}
	
}
