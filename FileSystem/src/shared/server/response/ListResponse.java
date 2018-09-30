package shared.server.response;


import java.util.HashMap;
import java.util.Map;

/**
 * A response to the list command. It contains the file names and
 * the users having locks on each file.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public class ListResponse extends Response {
	
	private Map<String, String> filesLocked;
	
	public ListResponse() {
		super();
		this.filesLocked = new HashMap<String, String>();
	}
	
	/**
	 * Add a file to the response;
	 * 
	 * @param fileName The file to add.
	 * @param username The user having the lock. Can be null.
	 */
	public void addFile(String fileName, String username) {
		this.filesLocked.put(fileName, username);
	}
	
	/**
	 * Prints the list of the files and the user having the lock.
	 */
	@Override
	public String toString() {
		StringBuilder message = new StringBuilder();
		for(String fileName: filesLocked.keySet()) {
			message.append("* ");
			message.append(fileName);
			String user = filesLocked.get(fileName) == null ? "non vérouillé": filesLocked.get(fileName);
			message.append(" ");
			message.append(user);
			message.append("\n");
		}
		return message.toString();
	}

}
