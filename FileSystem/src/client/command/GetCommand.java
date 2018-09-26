package client.command;

import shared.client.InvalidArgumentsException;
import shared.auth.Credentials;
import shared.server.FileServerInterface;

import java.rmi.RemoteException;

public class GetCommand extends Command {

    private FileServerInterface server;
    private Credentials credentials;

    public GetCommand(FileServerInterface server, Credentials cred, String[] params) {
        super(params);
        this.server = server;
        this.credentials = cred;
    }

    @Override
    public void execute() throws RemoteException, InvalidArgumentsException {
        if (this.args.length < 3) {
            throw new InvalidArgumentsException();
        }
        // TODO
        this.server.get(this.credentials, this.args[2], null);
    }
}
