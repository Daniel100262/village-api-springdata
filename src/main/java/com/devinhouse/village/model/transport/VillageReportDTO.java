package com.devinhouse.village.model.transport;

import java.math.BigDecimal;

public class VillageReportDTO {
	private final Float cost;
    private final Float initialBudget;
    private final BigDecimal villagersCostSum;
    private final String villagerWithHigherCost;
    
 
	public VillageReportDTO(Float cost, Float initialBudget, BigDecimal villagersCostSum,
			String villagerWithHigherCost) {
		super();
		this.cost = cost;
		this.initialBudget = initialBudget;
		this.villagersCostSum = villagersCostSum;
		this.villagerWithHigherCost = villagerWithHigherCost;
	}
	
	
	public Float getCost() {
		return cost;
	}
	public Float getInitialBudget() {
		return initialBudget;
	}
	public BigDecimal getVillagersCostSum() {
		return villagersCostSum;
	}
	public String getVillagerWithHigherCost() {
		return villagerWithHigherCost;
	}
    
   
}
