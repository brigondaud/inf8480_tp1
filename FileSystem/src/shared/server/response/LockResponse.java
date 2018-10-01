package shared.server.response;

import java.io.IOException;

import shared.files.FileManager;

/**
 * A response to the lock command. It sends the success state
 * of the lock, and the user having the lock on the requested file.
 * In case of success, the user id corresponds to the user who made
 * the request.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public class LockResponse extends Response {
	
	/**
	 * The file on which the lock was requested.
	 */
	private String fileName;
	
	/**
	 * The success of the lock operation.
	 */
	private boolean success;
	
	/**
	 * The content of the file can be sent if the user local file
	 * is not up-to-date. Thus it can be nullable.
	 */
	private byte[] content;
	
	/**
	 * The id of the user having the lock.
	 */
	private String user;
	
	/**
	 * The lock response is created with information on the file requested for
	 * being locked and the success of the operation. The user having the lock
	 * is also given.
	 * 
	 * @param fileName File name locked.
	 * @param success The success state.
	 * @param user The user having the lock (can be the user who made the request).
	 * @param content The content of the file if the local copy is out-of-date.
	 * @throws IOException 
	 */
	public LockResponse(String fileName, boolean success, String user, byte[] content) {
		super();
		this.fileName = fileName;
		this.success = success;
		this.user = user;
		this.content = content;
	}
	
	/**
	 * The user needs to get the last version of the file
	 * if his local copy is not up-to-date.
	 */
	@Override
	protected void onReception(FileManager fileManager) {
		// If the returned content is not null, then the file
		// is out-of-date: need to write the provided content.
		if(content == null) return;
		try {
			fileManager.write(fileName, content);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Message change based on the success of the lock command.
	 */
	@Override
	public String toString() {
		if(success) return fileName + " verrouillé.";
		return fileName + " est déjà vérouillé par " + user;
	}

}
