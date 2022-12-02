package com.capeelectric.service;

import java.io.IOException;

import com.capeelectric.exception.ChangePasswordException;
import com.capeelectric.exception.ForgotPasswordException;
import com.capeelectric.exception.UpdatePasswordException;
import com.capeelectric.exception.UserException;
import com.capeelectric.model.User;

public interface UserDetailsService {

	public User saveUser(User user) throws UserException;
	
	public User findByUserName(String email) throws ForgotPasswordException, IOException;
	
	public User updatePassword(String email, String password, Integer otp) throws UpdatePasswordException;
	
	public User changePassword(String email, String oldPassword, String password) throws ChangePasswordException;
	
	public User retrieveUserInformation(String email) throws UserException;
	
	public User updateUserProfile(User user);
	
}
