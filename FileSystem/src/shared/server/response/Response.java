package shared.server.response;

import java.io.IOException;
import java.io.Serializable;

import server.FileServer;
import shared.files.FileManager;

/**
 * A reponse sent by the file server to the client. It is used to factorize
 * the different responses sent by the server. A response can have some tasks
 * to execute when being received.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public abstract class Response implements Serializable {
	
	/**
	 * A response is executed at its reception for possible client side
	 * actions and displaying the results.
	 * @return
	 */
	public String execute(FileManager fileManager) {
		// Set the working directory to match the client's one.
		String execDir = System.getProperty("user.dir");
		try {
			fileManager.setWorkingDirectory(execDir + System.getProperty("file.separator") + FileManager.CLIENT_FILES_PATH);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		this.onReception(fileManager);
		return this.toString();
	}
	
	/**
	 * The actions that must be performed on reception.
	 * Does nothing by default.
	 */
	protected void onReception(FileManager fileManager) {
		// Does nothing by default
	}
	
	/**
	 * Each response has to override toString so that it can be
	 * directly printed to the user at the reception.
	 */
	@Override
	public abstract String toString();
}
