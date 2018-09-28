package shared.server.response;

/**
 * A response to the get command. Sends the content of
 * a file if the version on the file system is more recent
 * compared to the file on the client side.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public class GetResponse extends Response {
	
	private String fileName;
	
	private byte[] content;
	
	public GetResponse(String fileName, byte[] content) {
		this.fileName = fileName;
		this.content = content;
	}
	
	/**
	 * Writes the file to the client file system.
	 */
	@Override
	protected void onReception() {
		// TODO
	}

	@Override
	public String toString() {
		return fileName + " synchronisé.";
	}

}
