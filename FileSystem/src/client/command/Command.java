package client.command;

/**
 * Abstract class defining the common interface for all concrete requests
 * Implementing "Command" and "Template Method" design patterns
 */
public abstract class Command {
    protected String[] params;

    public Command(String[] params) {
        this.params = params;
    }

}
