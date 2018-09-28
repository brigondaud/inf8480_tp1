package shared.auth;

import java.io.Serializable;

/**
 * Simple Class used to easily manipulate users credentials in the system
 */
public class Credentials implements Serializable {
    private String login;
    private String password;

    public Credentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /* Getters & Setters */

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Compares two credentials based on their login.
     */
    @Override
    public boolean equals(Object other) {
    	if(other == null) return false;
    	if(!(other instanceof Credentials)) return false;
    	return login.equals(((Credentials)other).getLogin());
    }
    
    @Override
    public String toString() {
    	return this.login + ':' + this.password;
    }
}
