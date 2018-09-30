package client.command;

import shared.auth.Credentials;
import shared.server.FileServerInterface;
import shared.server.response.Response;

import java.io.File;
import java.rmi.RemoteException;

public class SyncCommand extends Command {

    private FileServerInterface server;
    private Credentials credentials;

    public SyncCommand(FileServerInterface server, Credentials cred, String[] params) {
        super(params);
        this.server = server;
        this.credentials = cred;
    }

    @Override
    public Response execute() throws RemoteException {
        return this.server.syncLocalDirectory(this.credentials);
    }
}
