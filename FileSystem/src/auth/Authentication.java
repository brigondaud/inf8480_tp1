package auth;

import shared.auth.AuthenticationInterface;
import shared.auth.Credentials;
import shared.files.FileManager;
import shared.server.response.NewResponse;
import shared.server.response.Response;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * The authentication server receives client RMI calls for managing connexions in the system.
 *
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public class Authentication implements AuthenticationInterface {

    private static final String METADATA_FILE = "auth.metadata";

    private Map<String, String> usersEntry;

    public Authentication() {
        try {
            FileManager fileManager = FileManager.createAuthenticationManager();
            if (fileManager.exists(METADATA_FILE)) {
                this.usersEntry = fileManager.deserializeMap(METADATA_FILE);
            } else {
                this.usersEntry = new HashMap<>();
            }
        } catch (Exception e) {
        	// Use empty credentials set in case of failure.
        	this.usersEntry = new HashMap<>();
        	System.err.println("Cannot retrieve server metadata, creating empty lock set.");
        }
    }

    @Override
    public synchronized Response newUser(String login, String password) {
        Credentials credentials = new Credentials(login, password);
        if (this.usersEntry.containsKey(login)) {
            return new NewResponse(credentials, false);
        } else {
            usersEntry.put(login, password);
            try {
				FileManager.createAuthenticationManager().write(METADATA_FILE, usersEntry);
			} catch (IOException e) {
				System.err.println("Cannot save the credentials data in metadata file.");
				e.printStackTrace();
				// Do not exit, the users can still use their in memory credentials.
			}
            return new NewResponse(credentials, true);
        }
    }

    @Override
    public boolean verify(String login, String password) {
        if (!this.usersEntry.containsKey(login)) {
            return false;
        } else {
            // If there is an entry for login, check if passwords matches
            return this.usersEntry.get(login).equals(password);
        }
    }

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Authentication";
            AuthenticationInterface auth = new Authentication();
            AuthenticationInterface stub =
                    (AuthenticationInterface) UnicastRemoteObject.exportObject(auth, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("Authentication server is ready");
        } catch (Exception e) {
            System.err.println("Authentication exception:");
            e.printStackTrace();
        }
    }
}
