package client.command;

import client.Client;
import shared.client.InvalidArgumentsException;
import shared.auth.Credentials;
import shared.files.FileManager;
import shared.files.MD5Checksum;
import shared.server.FileServerInterface;
import shared.server.response.Response;

import java.io.File;
import java.io.IOException;
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
    public Response execute() throws IOException, RemoteException, InvalidArgumentsException {
        if (this.args.length < 3) {
            throw new InvalidArgumentsException(this.args[Client.commandIndex]);
        }
        String fileName = this.args[2];
        FileManager fileManager = FileManager.createClientManager();
        MD5Checksum checksum;
        if(fileManager.exists(fileName))
        	checksum = fileManager.checksum(fileName);
        else
        	checksum = null;
        return this.server.get(this.credentials, fileName, checksum);
    }
}
