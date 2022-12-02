package com.capeelectric.response;

import java.io.Serializable;

import com.capeelectric.model.User;

/**
 * 
 * @author capeelectricsoftware
 *
 */
public class AuthenticationResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final User users;
	public AuthenticationResponse(String jwttoken, User users) {
		this.jwttoken = jwttoken;
		this.users = users;
	}
	public String getToken() {
		return this.jwttoken;
	}
	public User getUsers() {
		return users;
	}
	
}