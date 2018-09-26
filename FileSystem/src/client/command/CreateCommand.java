package client.command;

import shared.client.InvalidArgumentsException;
import shared.auth.Credentials;
import shared.server.FileServerInterface;

import java.rmi.RemoteException;

public class CreateCommand extends Command {

    private FileServerInterface server;
    private Credentials credentials;

    public CreateCommand(FileServerInterface server, Credentials cred, String[] params) {
        super(params);
        this.server = server;
        this.credentials = cred;
    }

    @Override
    public void execute() throws RemoteException, InvalidArgumentsException {
        if (this.args.length < 3) {
            throw new InvalidArgumentsException(this.args[1]);
        }
        String fileName = this.args[2];
        if (!this.server.create(this.credentials, fileName)) {
            System.out.println("Le fichier n'a pas pu être créé.");
        } else {
            System.out.println(fileName + " ajouté.");
        }
    }
}
