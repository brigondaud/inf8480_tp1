package auth;

import shared.auth.AuthenticationInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lopon on 18-09-12.
 */
public class Authentication implements AuthenticationInterface {

    private Map<String, String> usersEntry;

    public Authentication() {
        this.usersEntry = new HashMap<String, String>();
    }

    @Override
    public void newUser(String login, String password) {

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
        } catch (Exception e) {
            System.err.println("ComputeEngine exception:");
            e.printStackTrace();
        }
    }
}
