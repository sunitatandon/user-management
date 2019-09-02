package com.mgmt.user.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;
import java.util.Objects;

public class User {

	private String id;

	@NotEmpty(message = "Please provide username")
	private String username;

	@NotEmpty(message = "Please provide first name")
	private String firstName;

	private String lastName;

	@NotEmpty(message = "Please provide email address")
	@Email(message = "Please provide valid email address")
	private String emailAddress;

	@NotEmpty(message = "Please provide mobileNumber number")
	@Length(min = 10, max = 10, message = "Mobile number must be 10 digits long")
	private String mobileNumber;

	private String version;

	private Date creationTimestamp;

	private Date versionTimestamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public Date getVersionTimestamp() {
		return versionTimestamp;
	}

	public void setVersionTimestamp(Date versionTimestamp) {
		this.versionTimestamp = versionTimestamp;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User) o;
		return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(firstName, user.firstName) && Objects
				.equals(lastName, user.lastName) && Objects.equals(emailAddress, user.emailAddress) && Objects.equals(
				mobileNumber, user.mobileNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, firstName, lastName, emailAddress, mobileNumber);
	}
}
