package com.retail_cloud.employee_management_system.exception;

import java.time.LocalDateTime;

public class ErrorDetails {

	public ErrorDetails(LocalDateTime timeStamp, String message, String details) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.details = details;
	}

	private LocalDateTime timeStamp;
	
	private String message; 
	
	private String details;
}
