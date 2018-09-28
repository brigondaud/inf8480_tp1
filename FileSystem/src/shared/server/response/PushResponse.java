package shared.server.response;

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
	 */
	public PushResponse(String fileName, boolean success) {
		this.fileName = fileName;
		this.success = success;
	}

	@Override
	public String toString() {
		if(success) return fileName + "a �t� envoy� au serveur.";
		return "Op�ration refus�e, vous devez d'abord v�rouiller le fichier";
	}

}
