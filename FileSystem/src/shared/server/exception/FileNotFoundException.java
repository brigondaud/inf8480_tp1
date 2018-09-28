package shared.server.exception;

import java.rmi.RemoteException;

/**
 * Raised exception when a file is not found.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public class FileNotFoundException extends RemoteException {
	
	public FileNotFoundException(String fileName) {
		super("File " + fileName + " was not found.");
	}
}
