package shared.client;

public class InvalidArgumentsException extends Exception {

    private static final String message = "Invalid arguments given for command: ";

    public InvalidArgumentsException(String commandName) {
        super(message + commandName);
    }
}
