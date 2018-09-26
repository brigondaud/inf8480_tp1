package client.command;

import shared.client.InvalidArgumentsException;
import shared.auth.Credentials;
import shared.server.FileServerInterface;
import shared.server.response.Response;

import java.io.File;
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
    public Response execute() throws RemoteException, InvalidArgumentsException {
        if (this.args.length < 3) {
            throw new InvalidArgumentsException(this.args[1]);
        }
        String fileName = this.args[2];
        File file = this.server.get(this.credentials, fileName, null);
        return null; //TODO
    }
}
