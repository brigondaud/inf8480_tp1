package shared.server.response;

/**
 * A reponse to the create command. Used just to print a message in
 * case of success.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public class CreateResponse extends Response {
	
	private String fileCreated;
	
	public CreateResponse(String fileCreated) {
		this.fileCreated = fileCreated;
	}

	@Override
	public String toString() {
		return "fichier " + this.fileCreated + " ajouté";
	}

}
