package client.command;

import shared.server.FileServerInterface;

import java.rmi.RemoteException;

public class SyncCommand extends Command {

    public SyncCommand(String[] params) {
        super(params);
    }

    @Override
    public void execute(FileServerInterface server) throws RemoteException {
        server.syncLocalDirectory(this.credentials);
    }
}
