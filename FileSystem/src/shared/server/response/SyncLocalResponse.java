package shared.server.response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import shared.files.FileManager;

/**
 * A response to the syncLocalDirectory command. It receives
 * every files and their content from the file server
 * and overrides every file locally.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public class SyncLocalResponse extends Response {
	
	/**
	 * Holds all the information about the files and their content
	 * of the file system.
	 */
	private Map<String, byte[]> files;
	
	public SyncLocalResponse() {
		super();
		this.files = new HashMap<String, byte[]>();
	}
	
	/**
	 * Adds a file based on his name and on his content.
	 * @param name File's name to add.
	 * @param content File's content to add.
	 */
	public void addFile(String name, byte[] content) {
		if(files.get(name) != null) throw new InternalError("File has already been added to the sync response.");
		this.files.put(name, content);
	}
	
	/**
	 * Overrides every file locally.
	 * @throws  
	 */
	@Override
	protected void onReception(FileManager fileManager) {
		for(String file: files.keySet()) {
			try {
				fileManager.write(file, files.get(file));
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	@Override
	public String toString() {
		return "Fichiers synchronisés avec le serveur";
	}

}
