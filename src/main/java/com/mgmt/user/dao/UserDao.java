package com.mgmt.user.dao;

import com.mgmt.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

	Optional<User> getUser(String id);

	List<User> getUsers();

	User addUser(User user);

	User updateUser(User user);

	User deleteUser(String id);
}
