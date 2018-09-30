package shared.server.response;

import java.io.IOException;

import shared.files.FileManager;

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
		super();
		this.fileName = fileName;
		this.content = content;
	}
	
	/**
	 * Writes the file to the client file system.
	 */
	@Override
	protected void onReception(FileManager fileManager) {
		if(content == null) return;
		try {
			fileManager.write(fileName, content);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public String toString() {
		if(content == null) return "Le fichier est déjà à jour";
		return fileName + " synchronisé";
	}

}
