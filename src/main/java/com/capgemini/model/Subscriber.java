package com.capgemini.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Subscriber {
    
	@NotNull 
	@NotEmpty(message = "Please enter requestor email id") 
	@Email(message = "Give valid requestor email id")
    @Size(max = 5, message = "It should not be more than 5 charaters")
	String requestor;
	
	@NotNull 
	@NotEmpty(message = "Please enter target email id") 
	@Email(message = "Give valid target email id")
    @Size(max = 5, message = "It should not be more than 5 charaters")
    String target;
    
       
	public Subscriber() {
		
	}

	
	public String getRequestor() {
		return requestor;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
    
    
}
