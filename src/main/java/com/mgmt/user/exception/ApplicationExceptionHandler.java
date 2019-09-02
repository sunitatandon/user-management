package com.mgmt.user.exception;

import com.mgmt.user.model.ErrorModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class ApplicationExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorModel> handleValidationException(MethodArgumentNotValidException e) {
		List<String> errors = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
		log.error("Request has validation errors: {}", errors);
		return ResponseEntity.badRequest().body(new ErrorModel(errors));
	}

	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<ErrorModel> handleInvalidRequestException(InvalidRequestException e) {
		log.error("Invalid request error: {}", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorModel(e.getMessage()));
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Void> handleUserNotFoundException(UserNotFoundException e) {
		log.error(e.getMessage());
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(KycNotFoundException.class)
	public ResponseEntity<Void> handleKycNotFoundException(KycNotFoundException e) {
		log.error(e.getMessage());
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorModel> handleException(Exception e) {
		log.error("Error while processing request: {}", e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorModel("Failed to process your request."));
	}
}
