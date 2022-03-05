package com.devinhouse.village.utils;

import java.util.List;

import com.devinhouse.village.model.UserCredential;
import com.devinhouse.village.model.UserRole;

public class UserCredentialUtils {
	
	public static final List<UserCredential> userCredentialsFilled = List.of(
		new UserCredential("jarmine.benjamin@gmail.com", "iJwQ@194eMeI7a*Z", List.of(new UserRole(1, "ADMIN"))),
		new UserCredential("delilah.blackwell@outlook.com", "iFIvjzO#hNy!qflT", List.of(new UserRole(1, "USER"))),
		new UserCredential("escott.england@yahoo.com", "OaFR&P!pgzVpowHd", List.of(new UserRole(1, "ADMIN"))),
		new UserCredential("nero.bradford@hotmail.com", "CboPrnT#a3o@vssC", List.of(new UserRole(1, "ADMIN"))),
		new UserCredential("ralph.holt@aol.com", "*z9s@#mEH^%7Bnuk", List.of(new UserRole(1, "USER"))),
		new UserCredential("garrett.henson@icloud.com", "7XZ0^MwFLdGza@BP", List.of(new UserRole(1, "ADMIN"))),
		new UserCredential("angela.grimes@live.com", "bxM5Ku$sVWoYr#ZM", List.of(new UserRole(1, "USER")))
	);
}
