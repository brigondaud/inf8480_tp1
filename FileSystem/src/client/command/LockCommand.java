package client.command;

import client.Client;
import shared.client.InvalidArgumentsException;
import shared.auth.Credentials;
import shared.files.FileManager;
import shared.files.MD5Checksum;
import shared.server.FileServerInterface;
import shared.server.response.Response;

import java.io.IOException;
import java.rmi.RemoteException;

public class LockCommand extends Command {

    private FileServerInterface server;
    private Credentials credentials;

    public LockCommand(FileServerInterface server, Credentials cred, String[] params) {
        super(params);
        this.server = server;
        this.credentials = cred;
    }

    @Override
    public Response execute() throws IOException, InvalidArgumentsException {
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
        return this.server.lock(this.credentials, fileName, checksum);
    }

}
