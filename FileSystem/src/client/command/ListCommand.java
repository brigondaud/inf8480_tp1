package client.command;

import shared.auth.Credentials;
import shared.server.FileServerInterface;

import java.io.File;
import java.rmi.RemoteException;

public class ListCommand extends Command {

    private FileServerInterface server;
    private Credentials credentials;

    public ListCommand(FileServerInterface server, Credentials cred, String[] params) {
        super(params);
        this.server = server;
        this.credentials = cred;
    }

    @Override
    public void execute() throws RemoteException {
        String[] files = this.server.list(this.credentials);
        for (String file: files) {
            System.out.println("* " + file);
        }
    }
}
