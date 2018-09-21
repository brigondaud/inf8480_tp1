package shared.client;

/**
 * Abstract class defining the common interface for all concrete requests
 * Implementing "Command" and "Template Method" design patterns
 */
public abstract class Request {

    public Request() {

    }

    public void execute(String login, String password) {
        // TODO Create an object containing the credentials ?
        requestBody();
    }

    /**
     * Method implementing the actual logic of the request, needs to be redefined
     * by concrete subclasses
     */
    public abstract void requestBody();
}
