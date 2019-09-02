package com.mgmt.user.api.impl;

import com.mgmt.user.api.UserApi;
import com.mgmt.user.dao.UserDao;
import com.mgmt.user.exception.InvalidRequestException;
import com.mgmt.user.exception.UserNotFoundException;
import com.mgmt.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class UserApiService implements UserApi {

	private static final Logger log = LoggerFactory.getLogger(UserApiService.class);

	private UserDao userDao;

	@Autowired
	public UserApiService(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User getUser(String id) {
		return userDao.getUser(id).orElseThrow(() -> new UserNotFoundException(format("User with id %s not found.", id)));
	}

	@Override
	public List<User> getUsers() {
		return userDao.getUsers();
	}

	@Override
	public User addUser(User user) {
		return userDao.addUser(user);
	}

	@Override
	public User updateUser(String id, User user) {
		Optional<User> existingUser = userDao.getUser(id);
		if (!existingUser.isPresent()) {
			log.error("No user found with ID for update request: {}", id);
			throw new InvalidRequestException("Please provide valid User ID.");
		}
		if (!existingUser.get().getVersion().equals(user.getVersion())) {
			log.error("Cannot update user with id: {} as provided version: {} does not match the current version: {}", id, user.getVersion(),
					existingUser.get().getVersion());
			throw new InvalidRequestException("Please provide the latest User version");
		}
		user.setId(id);
		return userDao.updateUser(user);
	}

	@Override
	public void removeUser(String id, String version) {
		Optional<User> existingUser = userDao.getUser(id);
		if (!existingUser.isPresent()) {
			log.error("No user found with ID for delete request: {}", id);
			throw new InvalidRequestException("Please provide valid User ID.");
		}
		if (!existingUser.get().getVersion().equals(version)) {
			log.error("Cannot update user with id: {} as provided version: {} does not match the current version: {}", id, version,
					existingUser.get().getVersion());
			throw new InvalidRequestException("Please provide the latest User version");
		}
		userDao.deleteUser(id);
	}
}
