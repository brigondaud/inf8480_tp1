package shared.server.response;

import shared.auth.Credentials;
import shared.files.FileManager;

import java.io.IOException;

public class NewResponse extends Response {

    private Credentials credentials;

    private boolean success;

    public NewResponse(Credentials credentials, boolean success) {
        super();
        this.credentials = credentials;
        this.success = success;
    }

    @Override
    public void onReception(FileManager fileManager) {
        if (this.success) {
            String execDir = System.getProperty("user.dir");
            try {
                fileManager.setWorkingDirectory(execDir);
                // The entries need to be serialized on every update
                fileManager.write(".credentials", credentials);
            } catch (IOException ioException) {
                System.err.println("I/O exception happened during write operation");
                ioException.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        if (this.success) {

            return  "Utilisateur " + this.credentials.getLogin() + " enregistré.";
        } else {
            return "Echec lors de l'enregistrement de l'utilisateur " + this.credentials.getLogin();
        }
    }

}
