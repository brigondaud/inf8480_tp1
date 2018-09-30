package client.command;

import client.Client;
import shared.client.InvalidArgumentsException;
import shared.auth.Credentials;
import shared.files.FileManager;
import shared.server.FileServerInterface;
import shared.server.response.Response;

import java.io.IOException;
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
    public Response execute() throws IOException, RemoteException, InvalidArgumentsException {
        if (this.args.length < 3) {
            throw new InvalidArgumentsException(this.args[Client.commandIndex]);
        }
        FileManager fileManager = new FileManager();
        String fileName = this.args[2];
        String fileContent = new String(fileManager.read(fileName));
        return this.server.push(this.credentials, fileName, fileContent);
    }
}
