package com.mgmt.user.model;

import java.util.List;

public class ErrorModel {

	private String error;
	private List<String> errors;

	public ErrorModel(String error) {
		this.error = error;
	}

	public ErrorModel(List<String> errors) {
		this.errors = errors;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
