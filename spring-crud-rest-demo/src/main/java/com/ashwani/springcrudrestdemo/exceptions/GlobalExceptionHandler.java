package com.ashwani.springcrudrestdemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ashwani.springcrudrestdemo.model.CustomerErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<CustomerErrorResponse> handleException(CustomerNotFoundException ex) {
		CustomerErrorResponse customerErrorResponse = new CustomerErrorResponse();
		customerErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		customerErrorResponse.setMessage(ex.getMessage());
		customerErrorResponse.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<CustomerErrorResponse>(customerErrorResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<CustomerErrorResponse> handleException(Exception ex) {
		CustomerErrorResponse customerErrorResponse = new CustomerErrorResponse();
		customerErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		customerErrorResponse.setMessage(ex.getCause().toString());
		customerErrorResponse.setTimestamp(System.currentTimeMillis());
		return new ResponseEntity<CustomerErrorResponse>(customerErrorResponse,HttpStatus.BAD_REQUEST);
	}

}
