package client.command;

import shared.Client.InvalidArgumentsException;
import shared.auth.Credentials;
import shared.server.FileServerInterface;

import java.rmi.RemoteException;

/**
 * Abstract class defining the common interface for all concrete requests
 * Implementing "Command" and "Template Method" design patterns
 */
public abstract class Command {

    protected String[] args;
    protected Credentials credentials;

    public Command(String[] args) {
        this.args = args;
    }

    public abstract void execute(FileServerInterface server) throws RemoteException, InvalidArgumentsException;

    protected Credentials retrieveCredentials() {
        // TODO
        return null;
    }

}
