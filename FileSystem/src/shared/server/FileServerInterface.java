package shared.server;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.zip.Checksum;

/**
 * The file system RMI interface. It contains every operations that can be done
 * with the file server.
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public interface FileServerInterface extends Remote {
	
	boolean create(String name) throws RemoteException;
	File[] list() throws RemoteException;
	File[] syncLocalDirectory() throws RemoteException;
	File get(String name,  Checksum checksum) throws RemoteException;
	boolean lock(String name, Checksum checksum) throws RemoteException; //TODO: return the id of the user instead of a boolean
	boolean push(String name, String contenu) throws RemoteException; //TODO: see if string is enough for file content
}
