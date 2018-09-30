package shared.server.response;

public class NewResponse extends Response {

    private String login;

    private boolean success;

    public NewResponse(String login, boolean success) {
        super();
        this.login = login;
        this.success = success;
    }

    @Override
    public String toString() {
        if (this.success) {

            return  "Utilisateur " + this.login + " enregistré.";
        } else {
            return "Echec lors de l'enregistrement de l'utilisateur " + this.login;
        }
    }

}
