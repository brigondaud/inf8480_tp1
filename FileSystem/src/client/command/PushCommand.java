package client.command;

import shared.Client.InvalidArgumentsException;
import shared.server.FileServerInterface;

import java.rmi.RemoteException;

public class PushCommand extends Command {

    public PushCommand(String[] params) {
        super(params);
    }

    @Override
    public void execute(FileServerInterface server) throws RemoteException, InvalidArgumentsException {
        if (this.args.length < 4) {
            throw new InvalidArgumentsException();
        }
        server.push(this.credentials, this.args[2], this.args[3]);
    }
}
