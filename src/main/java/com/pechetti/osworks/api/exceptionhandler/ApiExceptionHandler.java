package com.pechetti.osworks.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pechetti.osworks.domain.exception.DomainException;
import com.pechetti.osworks.domain.exception.EntityNotFoundMException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(EntityNotFoundMException.class)
	public ResponseEntity<Object> handleEntityNotFound(DomainException ex, WebRequest request) {
		
		var status = HttpStatus.NOT_FOUND;
		
		var problem = new Problem();
		problem.setStatus(status.value());
		problem.setTitle(ex.getMessage());
		problem.setDate(OffsetDateTime.now());
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(DomainException.class)
	public ResponseEntity<Object> handleDomain(DomainException ex, WebRequest request) {
		
		var status = HttpStatus.BAD_REQUEST;
		
		var problem = new Problem();
		problem.setStatus(status.value());
		problem.setTitle(ex.getMessage());
		problem.setDate(OffsetDateTime.now());
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var fields = new ArrayList<Problem.Field>();
		
		for (ObjectError error: ex.getBindingResult().getAllErrors()) {
			String name = ((FieldError) error).getField();
			String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			fields.add(new Problem.Field(name, message));
		}
		
		var problem = new Problem();
		problem.setStatus(status.value());
		problem.setDate(OffsetDateTime.now());
		problem.setTitle("Invalid data, try again!");
		problem.setFileds(fields);
		
		
		return super.handleExceptionInternal(ex, problem, headers, status, request);
	}
	
}
