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
	
	private boolean success;
	
	public CreateResponse(String fileCreated, boolean success) {
		this.fileCreated = fileCreated;
		this.success = success;
	}

	@Override
	public String toString() {
		if(!success) return "Le fichier " + fileCreated + " existe d�j�";
		return "fichier " + this.fileCreated + " ajouté";
	}

}
