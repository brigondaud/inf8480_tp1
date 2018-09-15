package shared.auth;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for the authentication server.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public interface AuthenticationInterface extends Remote{
    /**
     * Create a new user in the authentication system
     * @param login The login of the user
     * @param password The password of the user
     */
    public void newUser(String login, String password) throws RemoteException;

    /**
     *
     * @param login The login of the user to verify
     * @param password The password of the user to verify
     * @return True if the credentials are valid, false otherwise
     * @throws RemoteException
     */
    public boolean verify(String login, String password) throws RemoteException;
}
