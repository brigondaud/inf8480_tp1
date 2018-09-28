package client.command;

import client.Client;
import shared.client.InvalidArgumentsException;
import shared.auth.Credentials;
import shared.server.FileServerInterface;
import shared.server.response.CreateResponse;
import shared.server.response.Response;

import java.rmi.RemoteException;

public class CreateCommand extends Command {

    private FileServerInterface server;
    private Credentials credentials;

    public CreateCommand(FileServerInterface server, Credentials cred, String[] params) {
        super(params);
        this.server = server;
        this.credentials = cred;
    }

    @Override
    public Response execute() throws RemoteException, InvalidArgumentsException {
        if (this.args.length < 3) {
            throw new InvalidArgumentsException(this.args[Client.commandIndex]);
        }
        String fileName = this.args[2];
        return this.server.create(this.credentials, fileName);
    }
}
