package shared.server.response;

public class NotExistingFileResponse extends Response {

	@Override
	public String toString() {
		return "Le fichier demandé n'existe pas.";
	}

}
