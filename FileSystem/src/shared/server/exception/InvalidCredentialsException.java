package shared.server.exception;

import shared.auth.Credentials;

import java.rmi.RemoteException;

/**
 * Thrown when bad authentication with the authentication server happens.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public class InvalidCredentialsException extends RemoteException {
	
	private static final String message = "Invalid credentials given: ";
	
	public InvalidCredentialsException(Credentials credentials) {
		super(InvalidCredentialsException.message + credentials.toString());
	}

}
