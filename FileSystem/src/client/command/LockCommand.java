package client.command;

import shared.Client.InvalidArgumentsException;
import shared.server.FileServerInterface;

import java.rmi.RemoteException;

public class LockCommand extends Command {

    public LockCommand(String[] params) {
        super(params);
    }

    @Override
    public void execute(FileServerInterface server) throws RemoteException, InvalidArgumentsException {
        if (this.args.length < 3) {
            throw new InvalidArgumentsException();
        }
        server.lock(this.credentials, this.args[2], null);
    }
}
