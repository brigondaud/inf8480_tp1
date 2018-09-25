package client.command;

import shared.server.FileServerInterface;

import java.rmi.RemoteException;

public class ListCommand extends Command {

    public ListCommand(String[] params) {
        super(params);
    }

    @Override
    public void execute(FileServerInterface server) throws RemoteException {
        server.list(this.credentials);
    }
}
