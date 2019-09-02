package com.mgmt.user.api;

import com.mgmt.user.model.User;

import java.util.List;

public interface UserApi {

	User getUser(String id);

	List<User> getUsers();

	User addUser(User user);

	User updateUser(String id, User user);

	void removeUser(String id, String version);

}
