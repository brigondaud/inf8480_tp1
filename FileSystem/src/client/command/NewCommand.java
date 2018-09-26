package client.command;

import shared.client.InvalidArgumentsException;
import shared.auth.AuthenticationInterface;

import java.rmi.RemoteException;

public class NewCommand extends Command {

    private AuthenticationInterface server;

    public NewCommand(AuthenticationInterface server, String[] args) {
        super(args);
        this.server = server;
    }

    @Override
    public void execute() throws RemoteException, InvalidArgumentsException {
        if (args.length < 4) {
            throw new InvalidArgumentsException(this.args[1]);
        }
        this.server.newUser(args[2], args[3]);
    }

}
