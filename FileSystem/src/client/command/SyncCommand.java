package client.command;

import shared.auth.Credentials;
import shared.server.FileServerInterface;

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
    public void execute() throws RemoteException {
        File[] files = this.server.syncLocalDirectory(this.credentials);
    }
}
