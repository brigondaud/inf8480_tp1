package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.zip.Checksum;

import com.sun.xml.internal.ws.api.pipe.ThrowableContainerPropertySet;

import shared.auth.AuthenticationInterface;
import shared.auth.Credentials;
import shared.files.FileManager;
import shared.server.FileServerInterface;
import shared.server.InvalidCredentialsException;

/**
 * The file server receives client RMI calls for manipulating the files,
 * and communicates with the authentication server to validate client requests.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public class FileServer implements FileServerInterface {
	
	/**
	 * The path to the files stored in the file system.
	 */
	private static final String FILE_PATH = "files";
	
	/**
	 * A remote reference to the authentication server.
	 */
	private AuthenticationInterface authenticationServer;
	
	/**
	 * A file manager to handle all the file manipulations.
	 */
	private FileManager fileManager;
	
	public static void main(String[] args) throws IOException, NotBoundException {
		FileServer fs = new FileServer();
		//TODO: recover the metadata from a possible crash before running the server ?
		fs.run();
	}
	
	/**
	 * Creates a new file server. It initiates its file manager to work in a specific directory,
	 * and gets a reference to the authentication interface.
	 * 
	 * @throws IOException if the file manager cannot initiate.
	 * @throws NotBoundException if authentication server is unreachable.
	 */
	public FileServer() throws IOException, NotBoundException {
		String execDir = System.getProperty("user.dir");
		this.fileManager = new FileManager();
		this.fileManager.setWorkingDirectory(execDir + System.getProperty("file.separator") + FileServer.FILE_PATH);

		//Getting a reference to the authentication server.
		Registry registry = LocateRegistry.getRegistry("127.0.0.1");
		this.authenticationServer = (AuthenticationInterface) registry.lookup("Authentication");
	}		
	
	@Override
	public boolean create(Credentials credentials, String name) throws RemoteException {
		verifyCredentials(credentials);
		try {
			if(!fileManager.exists(fileManager.buildFilePath(name)))
				return fileManager.create(name);
			return false;
		}
		catch (IOException e) {
			throw new RemoteException("Cannot create requested file");
		}
	}
	
	@Override
	public String[] list(Credentials credentials) throws RemoteException {
		verifyCredentials(credentials);
		return fileManager.list();
	}

	@Override
	public File[] syncLocalDirectory(Credentials credentials) throws RemoteException {
		verifyCredentials(credentials);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File get(Credentials credentials, String name, Checksum checksum) throws RemoteException {
		verifyCredentials(credentials);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lock(Credentials credentials, String name, Checksum checksum) throws RemoteException {
		verifyCredentials(credentials);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void push(Credentials credentials, String name, String contenu) throws RemoteException {
		verifyCredentials(credentials);
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Verifies the client credentials by calling the authentication
	 * server.
	 * @param credentials
	 * @throws InvalidCredentialsException if the authentication failed with
	 * the provided credentials.
	 */
	private void verifyCredentials(Credentials credentials) throws RemoteException {
		if(!this.authenticationServer.verify(credentials.getLogin(), credentials.getPassword()))
			throw new InvalidCredentialsException(credentials);
	}
	
	/**
	 * Runs the file server by registering it to the RMI registry.
	 */
	private void run() {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		try {
			FileServerInterface stub = (FileServerInterface) UnicastRemoteObject
					.exportObject(this, 0);
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("server", stub);
			System.out.println("File server ready.");
		} catch (ConnectException e) {
			System.err
					.println("Impossible de se connecter au registre RMI. Est-ce que rmiregistry est lancé ?");
			System.err.println();
			System.err.println("Erreur: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Erreur: " + e.getMessage());
		}
	}


}
