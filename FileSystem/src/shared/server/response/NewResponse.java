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
            try {
            	// The file manager does not operate in the client file system here.
                fileManager.setWorkingDirectory(System.getProperty("user.dir"));
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
            return "L'utilisateur " + this.credentials.getLogin() + " existe déjà";
        }
    }

}
