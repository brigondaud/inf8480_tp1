package auth;

import shared.auth.AuthenticationInterface;
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

    private static final String RECOVERY_FILE_PATH = "/auth";

    private static final String RECOVERY_FILE_NAME = "recovery";

    private Map<String, String> usersEntry;

    public Authentication() {
        try {
            FileManager fileManager = new FileManager();
            String execDir = System.getProperty("user.dir");
            fileManager.setWorkingDirectory(execDir + RECOVERY_FILE_PATH);
            // We need to check if a recovery file already exists
            if (fileManager.exists(RECOVERY_FILE_NAME)) {
                this.usersEntry = fileManager.deserializeMap(RECOVERY_FILE_NAME);
            } else {
                this.usersEntry = new HashMap<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response newUser(String login, String password) {
        if (this.usersEntry.containsKey(login)) {
            return new NewResponse(login, false);
        } else {
            usersEntry.put(login, password);
            // The entries need to be serialized on every update
            try {
                String execDir = System.getProperty("user.dir");
                FileManager fileManager = new FileManager();
                fileManager.setWorkingDirectory(execDir + RECOVERY_FILE_PATH);
                fileManager.write(RECOVERY_FILE_NAME, this.usersEntry);
            } catch (IOException ioe) {
                // TODO Manage the exception
            }
            return new NewResponse(login, true);
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
