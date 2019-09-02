package com.mgmt.user.controller;

import com.mgmt.user.api.KycApi;
import com.mgmt.user.api.UserApi;
import com.mgmt.user.exception.InvalidRequestException;
import com.mgmt.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.singletonList;

@RestController
@RequestMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	private UserApi userApi;

	private KycApi kycApi;

	@Autowired
	public UserController(UserApi userApi,
			KycApi kycApi) {
		this.userApi = userApi;
		this.kycApi = kycApi;
	}

	@GetMapping
	public ResponseEntity<List<User>> getUser(
			@RequestHeader String requestId,
			@RequestParam(required = false) String id) {
		if(StringUtils.isEmpty(id)) {
			return ResponseEntity.ok().body(userApi.getUsers());
		} else {
			User user = userApi.getUser(id);
			return ResponseEntity.ok().body(singletonList(user));
		}
	}

	@PostMapping
	public ResponseEntity<User> addUser(@RequestHeader String requestId,
			@RequestBody @Valid User user) {
		log.info("Adding new user with username: {}", user.getUsername());
		User savedUser = userApi.addUser(user);
		log.info("Added new user with username: {}, id: {}", user.getUsername(), user.getId());
		return ResponseEntity.ok().body(savedUser);
	}

	@PutMapping
	public ResponseEntity<User> updateUser(@RequestHeader String requestId,
			@RequestParam String id,
			@RequestBody @Valid User user) {
		log.info("Updating user with id: {}", id);
		User updatedUser = userApi.updateUser(id, user);
		log.info("Updated user with id: {}", id);
		return ResponseEntity.ok().body(updatedUser);
	}

	@DeleteMapping
	public ResponseEntity<String> deleteUser(@RequestHeader String requestId,
			@RequestParam String id,
			@RequestParam String version) {
		log.info("Removing user with id: {}, version: {}", id, version);
		userApi.removeUser(id, version);
		log.info("Removed user with id: {}", id);
		return ResponseEntity.ok(format("User with ID '%s' has been removed.", id));
	}

	@PostMapping("/kyc")
	public ResponseEntity<String> uploadKycDocuments(@RequestHeader String requestId,
			@RequestParam String userId,
			@RequestParam MultipartFile file) {
		if(file.isEmpty())
			throw new InvalidRequestException("Please select a file");
		User user = userApi.getUser(userId);
		kycApi.uploadKycDocs(userId, file);
		return ResponseEntity.ok().body(format("KYC documents for user %s have been uploaded successfully", user.getId()));
	}

	@GetMapping("/kyc")
	public ResponseEntity<Resource> downloadKycDocuments(@RequestHeader String requestId,
			@RequestParam String userId, HttpServletRequest request) {
		userApi.getUser(userId);
		Resource resource = kycApi.downloadKycDocs(userId);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			log.info("Could not determine file type.");
		}

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);

	}


}
