package com.glue.api.operations;

import com.glue.exceptions.GlueException;
import com.glue.struct.IUser;

public interface UserOperations {
	IUser createUser(IUser user) throws GlueException;

	IUser createUser(String firstName, String lastName, String email, String password) throws GlueException;

	IUser updateUser(IUser user) throws GlueException;

	void login(String username, String password) throws GlueException;

	void logout() throws GlueException;

	/**
	 * Registers the given user name / password pair that will be used to
	 * establish user identity when processing the authentication challenge sent
	 * by the target server in response to a request for a protected resource.
	 * 
	 * @param username the user name
	 * @param password the password
	 */
	void registerCredentials(String username, String password);

}
