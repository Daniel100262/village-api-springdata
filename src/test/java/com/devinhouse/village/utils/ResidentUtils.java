package com.devinhouse.village.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.devinhouse.village.model.Resident;

public class ResidentUtils {
	   
	   public static final List<Resident> residentsFullfilledWithUser =  
			    List.of(
					new Resident(1, "Jasmine ", "Benjamin", 47, LocalDate.parse("1974-03-19"), BigDecimal.valueOf(1500), "15881077016", UserCredentialUtils.userCredentialsFilled.get(0), "ADMIN", "jarmine.benjamin@gmail.com"),
		    		new Resident(2, "Delilah", "Blackwell", 40, LocalDate.parse("1982-03-08"), BigDecimal.valueOf(1800), "94405573034", UserCredentialUtils.userCredentialsFilled.get(1), "USER", "delilah.blackwell@outlook.com"),
		    		new Resident(3, "Scott", "England", 23, LocalDate.parse("1999-05-16"), BigDecimal.valueOf(2500), "19964520026", UserCredentialUtils.userCredentialsFilled.get(2), "ADMIN", "scott.england@yahoo.com"),
		    		new Resident(4, "Nero", "Bradford", 26, LocalDate.parse("1996-08-24"), BigDecimal.valueOf(2800), "61895597005", UserCredentialUtils.userCredentialsFilled.get(3), "ADMIN", "nero.bradford@hotmail.com"),
		    		new Resident(5, "Ralph", "Holt", 45, LocalDate.parse("1977-12-13"), BigDecimal.valueOf(3500), "27606479003", UserCredentialUtils.userCredentialsFilled.get(4), "USER", "ralph.holt@aol.com"),
		    		new Resident(6, "Garrett", "Henson", 30, LocalDate.parse("1992-01-03"), BigDecimal.valueOf(3800), "04315386030", UserCredentialUtils.userCredentialsFilled.get(5), "ADMIN", "garrett.henson@icloud.com"),
		    		new Resident(7, "Angela", "Grimes", 65, LocalDate.parse("1957-01-05"), BigDecimal.valueOf(4500), "24351281006", UserCredentialUtils.userCredentialsFilled.get(6), "USER", "angela.grimes@live.com")
		    	);
}
