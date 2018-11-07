package com.apap.tutorial8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDB;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	@Autowired
	private UserRoleDB userDb;
	
	
	@Override
	public UserRoleModel addUser(UserRoleModel user) {
		String pass = encrypt(user.getPassword());
		user.setPassword(pass);
		return userDb.save(user);
	}

	@Override
	public String encrypt(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}

	@Override
	public void updatePassword(String username, String newPass) {
		UserRoleModel user = userDb.findByUsername(username);
		user.setPassword(this.encrypt(newPass));
		userDb.save(user);
	}

	@Override
	public boolean checkIfValidOldPassword(UserRoleModel user, String oldPass) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if(passwordEncoder.matches(oldPass, user.getPassword())) {
			return true;
		}
		
		else {
			return false;
		}
		
	}

	@Override
	public UserRoleModel findByUsername(String username) {
		return userDb.findByUsername(username);
	}

	@Override
	public boolean checkIfPasswordValid(String newPass) {

		if(newPass.matches("^(?=.*[a-zA-Z])(?=\\w*[0-9])\\w{8,}$")) {
				return true;
		}
		
		else {
			return false;
		}
		
	}

}
