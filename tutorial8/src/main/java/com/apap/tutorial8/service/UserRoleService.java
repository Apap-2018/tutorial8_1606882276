package com.apap.tutorial8.service;

import com.apap.tutorial8.model.UserRoleModel;

public interface UserRoleService {
	UserRoleModel addUser(UserRoleModel user);
	UserRoleModel findByUsername(String username);
	public String encrypt(String password);
	void updatePassword(String username, String newPass);
	boolean checkIfValidOldPassword(UserRoleModel user, String oldPass);
	boolean checkIfPasswordValid(String newPass);
}
