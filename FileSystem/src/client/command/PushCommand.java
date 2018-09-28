package client.command;

import client.Client;
import shared.client.InvalidArgumentsException;
import shared.auth.Credentials;
import shared.server.FileServerInterface;
import shared.server.response.Response;

import java.rmi.RemoteException;

public class PushCommand extends Command {

    private FileServerInterface server;
    private Credentials credentials;

    public PushCommand(FileServerInterface server, Credentials cred, String[] params) {
        super(params);
        this.server = server;
        this.credentials = cred;
    }

    @Override
    public Response execute() throws RemoteException, InvalidArgumentsException {
        if (this.args.length < 4) {
            throw new InvalidArgumentsException(this.args[Client.commandIndex]);
        }
        this.server.push(this.credentials, this.args[2], this.args[3]);
        return null; //TODO
    }
}
