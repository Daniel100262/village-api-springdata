package com.devinhouse.village.model;

public enum InsertResidentResponseType {
	UNKNOW_ERROR(-1),
	SUCCESS_ADDED(0),
	DUPLICATED(1),
	INVALID_DATA(2),
	INVALID_PATTERN(3);
	
	
	private int responseCode;
	
	InsertResidentResponseType(int numVal) {
        this.responseCode = numVal;
    }

	public int getResponseCode() {
		return responseCode;
	}

}
