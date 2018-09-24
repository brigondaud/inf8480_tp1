package client.command;

import shared.auth.Credentials;

public class AuthCommand extends Command {
    private Credentials credentials;

    public AuthCommand(String[] params) {
        super(params);
    }


}
