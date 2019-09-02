package com.mgmt.user.dao.impl;

import com.mgmt.user.dao.UserDao;
import com.mgmt.user.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Optional.ofNullable;

@Repository
public class UserDaoMemoryImpl implements UserDao {

	private Map<String, User> users = new ConcurrentHashMap<>();

	@Override
	public Optional<User> getUser(String id) {
		return ofNullable(users.get(id));
	}

	@Override
	public List<User> getUsers() {
		return new ArrayList<>(users.values());
	}

	@Override
	public User addUser(User user) {
		user.setId(UUID.randomUUID().toString());
		user.setCreationTimestamp(new Date());
		user.setVersion(UUID.randomUUID().toString());
		user.setVersionTimestamp(new Date());
		return users.put(user.getId(), user);
	}

	@Override
	public User updateUser(User user) {
		user.setVersion(UUID.randomUUID().toString());
		user.setVersionTimestamp(new Date());
		return users.put(user.getId(), user);
	}

	@Override
	public User deleteUser(String id) {
		return users.remove(id);
	}
}
