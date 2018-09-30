package client.command;

import client.Client;
import shared.auth.Credentials;
import shared.client.InvalidArgumentsException;
import shared.files.FileManager;
import shared.server.response.Response;
import shared.auth.AuthenticationInterface;

import java.io.IOException;
import java.rmi.RemoteException;

public class NewCommand extends Command {

    private AuthenticationInterface server;

    public NewCommand(AuthenticationInterface server, String[] args) {
        super(args);
        this.server = server;
    }

    @Override
    public Response execute() throws RemoteException, InvalidArgumentsException, IOException {
        if (args.length < 4) {
            throw new InvalidArgumentsException(this.args[Client.commandIndex]);
        }
        Credentials credentials = new Credentials(args[2], args[3]);
        FileManager fileManager = new FileManager();
        fileManager.write(".credentials", credentials);
        return this.server.newUser(credentials.getLogin(), credentials.getPassword());
    }

}
