package shared.auth;

import shared.server.response.Response;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for the authentication server.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public interface AuthenticationInterface extends Remote {
    /**
     * Create a new user in the authentication system
     * @param login The login of the user
     * @param password The password of the user
     * @return true if the operation succeed, false if login was already used by an existing user
     */
    Response newUser(String login, String password) throws RemoteException;

    /**
     * Verify that given credentials are valid
     * @param login The login of the user to verify
     * @param password The password of the user to verify
     * @return true if the credentials are valid, false otherwise
     * @throws RemoteException
     */
    boolean verify(String login, String password) throws RemoteException;
}
