package com.retail_cloud.employee_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND)
public class CustomResourceNotFoundException extends RuntimeException {
	
	public CustomResourceNotFoundException(String message) {
		super(message);
	}

}
