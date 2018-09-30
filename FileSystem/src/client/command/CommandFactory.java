package client.command;

import client.Client;
import shared.client.InvalidArgumentsException;
import shared.auth.AuthenticationInterface;
import shared.auth.Credentials;
import shared.client.InvalidCommandException;
import shared.files.FileManager;
import shared.server.FileServerInterface;

import java.io.IOException;

public class CommandFactory {

    private FileServerInterface fileServer;
    private AuthenticationInterface authServer;

    public CommandFactory(FileServerInterface fileServer, AuthenticationInterface auth) {
        this.fileServer = fileServer;
        this.authServer = auth;
    }

    /**
     * Parse the arguments received as parameters and create a Command object if the syntax is correct
     *
     * @param args The arguments to parse
     * @return A Command object corresponding to user input
     */
    public Command createCommand(String[] args) throws InvalidArgumentsException, InvalidCommandException, IOException{
        if (args.length < 2) {
            throw new InvalidCommandException();
        }
        System.out.println("Command name : " + args[Client.commandIndex]);
        String commandName = args[Client.commandIndex];
        FileManager fileManager = new FileManager();
        Credentials credentials = fileManager.retrieveUserCredentials(".credentials");
        // Do the parsing on command name
        switch (commandName) {
            case "create":
                return new CreateCommand(this.fileServer, credentials, args);
            case "list":
                return new ListCommand(this.fileServer, credentials, args);
            case "syncLocalDirectory":
                return new SyncCommand(this.fileServer, credentials, args);
            case "get":
                return new GetCommand(this.fileServer, credentials, args);
            case "lock":
                return new LockCommand(this.fileServer, credentials, args);
            case "push":
                return new PushCommand(this.fileServer, credentials, args);
            case "new":
                return new NewCommand(this.authServer, args);
            default:
                throw new InvalidCommandException();
        }
    }

}
