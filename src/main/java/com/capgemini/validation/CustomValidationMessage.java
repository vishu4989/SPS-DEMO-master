package com.capgemini.validation;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.capgemini.model.Subscriber;

@ControllerAdvice
public class CustomValidationMessage extends ResponseEntityExceptionHandler {

	@Override
	  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	      HttpHeaders headers, HttpStatus status, WebRequest request) {
		System.out.println("--------------1111111111-------------------");
	    FriendManagementValidation errorDetails = new FriendManagementValidation(false, ex.getBindingResult().toString());
	    return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	  }
	
	 @ExceptionHandler(Exception.class)
	  public final ResponseEntity<Object> handleUserNotFoundException(Subscriber ex, WebRequest request) {
		 System.out.println("-----------22222222222----------------------");
		 FriendManagementValidation errorDetails = new FriendManagementValidation(false, "");
	    return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
	  }
}
