package com.capgemini.validation;

import org.springframework.stereotype.Component;

@Component
public class FriendManagementValidation {
   
	boolean status;
	String errorDescription;
	
	
	public FriendManagementValidation() {
		
	}
	
	public FriendManagementValidation(boolean status, String errorDescription) {
		super();
		this.status = status;
		this.errorDescription = errorDescription;
	}
	
	public boolean isStatus() {
		return status;
	}
	

	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	
}
