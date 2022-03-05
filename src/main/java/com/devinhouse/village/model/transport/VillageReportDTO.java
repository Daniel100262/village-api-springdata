package com.devinhouse.village.model.transport;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

public class VillageReportDTO implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
	private final Float cost;
    private final Float initialBudget;
    private final BigDecimal residentsCostSum;
    private final String residentWithHigherCost;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String reportEmailDestination;
    
 
	public VillageReportDTO(Float cost, Float initialBudget, BigDecimal villagersCostSum,
			String villagerWithHigherCost, String reportEmailDestination) {
		super();
		this.cost = cost;
		this.initialBudget = initialBudget;
		this.residentsCostSum = villagersCostSum;
		this.residentWithHigherCost = villagerWithHigherCost;
		this.reportEmailDestination = reportEmailDestination;
	}
	
	public VillageReportDTO(Float cost, Float initialBudget, BigDecimal villagersCostSum,
			String villagerWithHigherCost) {
		super();
		this.cost = cost;
		this.initialBudget = initialBudget;
		this.residentsCostSum = villagersCostSum;
		this.residentWithHigherCost = villagerWithHigherCost;
		this.reportEmailDestination = null;
	}
	
	
	public String getReportEmailDestination() {
		return reportEmailDestination;
	}
	public Float getCost() {
		return cost;
	}
	public Float getInitialBudget() {
		return initialBudget;
	}
	public BigDecimal getResidentsCostSum() {
		return residentsCostSum;
	}
	public String getResidentWithHigherCost() {
		return residentWithHigherCost;
	}
}
