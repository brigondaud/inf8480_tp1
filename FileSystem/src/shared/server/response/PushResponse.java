package shared.server.response;

import java.io.IOException;

/**
 * A response to a push command. The response informs about the
 * success of the push operation.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public class PushResponse extends Response {

	private String fileName;
	
	private boolean success;
	
	/**
	 * The response is initiated with the name of the pushed file.
	 * 
	 * @param fileName The pushed file name.
	 * @throws IOException 
	 */
	public PushResponse(String fileName, boolean success) {
		super();
		this.fileName = fileName;
		this.success = success;
	}

	@Override
	public String toString() {
		if(success) return fileName + " a été envoyé au serveur.";
		return "Opération refusée, vous devez d'abord vérouiller le fichier";
	}

}
