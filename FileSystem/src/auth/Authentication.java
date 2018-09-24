package auth;

import shared.auth.AuthenticationInterface;

import java.io.FileOutputStream;
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

    private Map<String, String> usersEntry;

    public Authentication() {
        this.usersEntry = new HashMap<String, String>();
    }

    @Override
    public boolean newUser(String login, String password) {
        if (this.usersEntry.containsKey(login)) {
            return false;
        } else {
            usersEntry.put(login, password);
            return true;
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
            String hostIp = "132.207.12.87";
            AuthenticationInterface auth = new Authentication();
            AuthenticationInterface stub = (AuthenticationInterface)UnicastRemoteObject.exportObject(auth, 0);
            Registry registry = LocateRegistry.getRegistry(hostIp);
            registry.rebind(name, stub);
        } catch (Exception e) {
            System.err.println("Authentication exception:");
            e.printStackTrace();
        }
    }
}
