package client.command;

import shared.client.InvalidArgumentsException;

import java.rmi.RemoteException;

/**
 * Abstract class defining the common interface for all concrete requests
 * Implementing "Command" and "Template Method" design patterns
 */
public abstract class Command {

    protected String[] args;

    public Command(String[] args) {
        this.args = args;
    }

    public abstract void execute() throws RemoteException, InvalidArgumentsException;

}
