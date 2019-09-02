package com.mgmt.user.exception;

public class KycNotFoundException extends RuntimeException {

	public KycNotFoundException(String message) {
		super(message);
	}
}
