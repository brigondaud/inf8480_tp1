package client.command;

import shared.Client.InvalidArgumentsException;

public class CommandFactory {

    public CommandFactory() {
        super();
    }

    /**
     * Parse the arguments received as parameters and create a Command object if the syntax is correct
     *
     * @param args The arguments to parse
     * @return A Command object corresponding to user input
     */
    public Command createCommand(String[] args) throws InvalidArgumentsException {
        if (args.length < 2) {
            throw new InvalidArgumentsException();
        }
        String commandName = args[1];
        if (commandName.equals("create")) {
            return new CreateCommand(args);
        } else if (commandName.equals("list")) {
            return new ListCommand(args);
        } else if (commandName.equals("syncLocalDirectory")) {
            return new SyncCommand(args);
        } else if (commandName.equals("get")) {
            return new GetCommand(args);
        } else if (commandName.equals("lock")) {
            return new LockCommand(args);
        } else if (commandName.equals("push")) {
            return new PushCommand(args);
        } else {
            throw new InvalidArgumentsException();
        }
    }
}
