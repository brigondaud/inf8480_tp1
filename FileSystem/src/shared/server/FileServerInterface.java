package shared.server;

import shared.auth.Credentials;
import shared.files.MD5Checksum;
import shared.server.response.CreateResponse;
import shared.server.response.GetResponse;
import shared.server.response.ListResponse;
import shared.server.response.LockResponse;
import shared.server.response.PushResponse;
import shared.server.response.SyncLocalResponse;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The file system RMI interface. It contains every operations that can be done
 * with the file server. Every operation must be authenticated using credentials
 * given to the authentication server in the first time.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public interface FileServerInterface extends Remote {
	
	/**
	 * Creates an empty file in the file system. The file is not created if it
	 * already exists.
	 * 
	 * @param credentials The user credentials.
	 * @param name
	 * @return True on success. False if the file already exists.
	 * @throws RemoteException
	 */
	public CreateResponse create(Credentials credentials, String name) throws RemoteException;
	
	/**
	 * Returns a list of the files in the file system.
	 * 
	 * @param credentials The user credentials.
	 * @return The file list.
	 * @throws RemoteException
	 */
	public ListResponse list(Credentials credentials) throws RemoteException;
	
	/**
	 * Pulls all the files and their content from the file system.
	 * 
	 * @param credentials The user credentials.
	 * @return
	 * @throws RemoteException
	 */
	public SyncLocalResponse syncLocalDirectory(Credentials credentials) throws RemoteException;
	
	/**
	 * Get the latest version of a file. If the file is already up-to-date 
	 * nothing is sent.
	 * 
	 * @param credentials The user credentials.
	 * @param name File's name to get.
	 * @param checksum The client current file checksum.
	 * @return The file if the client is not up-to-date.
	 * @throws RemoteException
	 */
	public GetResponse get(Credentials credentials, String name,  MD5Checksum checksum) throws RemoteException;
	
	/**
	 * Asks the server to lock a file. The latest version is given beforehand if
	 * the client is not up-to-date.
	 * 
	 * @param credentials The user credentials.
	 * @param name File's name to get the lock on.
	 * @param checksum The client file checksum.
	 * @return The operation success.
	 * @throws RemoteException If the file does not exist or if the lock is already given.
	 */
	public LockResponse lock(Credentials credentials, String name, MD5Checksum checksum) throws RemoteException;
	
	/**
	 * Sends a new file version to the file system. The new content replaces the former one
	 * and unlock the file (the file must be locked before the operation).
	 * 
	 * @param credentials The user credentials.
	 * @param name The file name.
	 * @param content The new file content.
	 * @return The operation success.
	 * @throws RemoteException if the file was not locked before the push operation.
	 */
	public PushResponse push(Credentials credentials, String name, String content) throws RemoteException;
}
