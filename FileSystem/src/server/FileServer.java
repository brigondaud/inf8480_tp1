package server;

import java.io.IOException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Checksum;

import shared.auth.AuthenticationInterface;
import shared.auth.Credentials;
import shared.files.FileManager;
import shared.server.FileServerInterface;
import shared.server.exception.FileNotFoundException;
import shared.server.exception.InvalidCredentialsException;
import shared.server.response.CreateResponse;
import shared.server.response.GetResponse;
import shared.server.response.ListResponse;
import shared.server.response.LockResponse;
import shared.server.response.PushResponse;
import shared.server.response.SyncLocalResponse;

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
	
	/**
	 * Used to associate the users to files locked.
	 */
	private Map<String, Credentials> locks;
	
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

		this.locks = new HashMap<String, Credentials>();
		//TODO: load an existing saved lock file in case of server crash.
		
		//Getting a reference to the authentication server.
		Registry registry = LocateRegistry.getRegistry("127.0.0.1");
		this.authenticationServer = (AuthenticationInterface) registry.lookup("Authentication");
	}		
	
	@Override
	public synchronized CreateResponse create(Credentials credentials, String name) throws RemoteException {
		verifyCredentials(credentials);
		try {
			if(!fileManager.exists(fileManager.buildFilePath(name)))
				if(fileManager.create(name)) {
					return new CreateResponse(name);
				}
			return null;
		}
		catch (IOException e) {
			throw new RemoteException("Cannot create requested file");
		}
	}
	
	@Override
	public ListResponse list(Credentials credentials) throws RemoteException {
		verifyCredentials(credentials);
		String[] fileNames = fileManager.list();
		ListResponse response = new ListResponse();
		for(String file: fileNames) {
			Credentials user = locks.get(file);
			if(user == null) response.addFile(file, null);
			else response.addFile(file, user.getLogin());
		}
		return response;
	}

	@Override
	public synchronized SyncLocalResponse syncLocalDirectory(Credentials credentials) throws RemoteException {
		verifyCredentials(credentials);
		SyncLocalResponse response = new SyncLocalResponse();
		for(String file: fileManager.list()) {
			try {
				response.addFile(file, fileManager.read(file));
			} catch (IOException e) {
				throw new RemoteException("File server read error: unaible to sync the local directory.");
			}
		}
		return response;
	}

	@Override
	public synchronized GetResponse get(Credentials credentials, String name, Checksum checksum) throws RemoteException {
		verifyCredentials(credentials);
		if(!fileManager.exists(name)) throw new FileNotFoundException(name);
		// Check if file not updated.
		if(checksum != null && fileManager.checksum(name).equals(checksum)) return null;
		// At this point the file must be sent in any case.
		try {
			return new GetResponse(name, fileManager.read(name));
		} catch (IOException e) {
			throw new RemoteException("Cannot read the requested file.");
		}
	}

	@Override
	public synchronized LockResponse lock(Credentials credentials, String name, Checksum checksum) throws RemoteException {
		verifyCredentials(credentials);
		if(!fileManager.exists(name)) throw new FileNotFoundException(name);
		byte[] content = null;
		if(locks.containsKey(name)) return new LockResponse(name, false, locks.get(name).getLogin(), content);
		locks.put(name, credentials);
		if(checksum != null && fileManager.checksum(name) != checksum)
			try {
				content = fileManager.read(name);
			} catch (IOException e) {
				throw new RemoteException("Cannot read the requested file.");
			}
		return new LockResponse(name, true, credentials.getLogin(), content);
	}

	@Override
	public PushResponse push(Credentials credentials, String name, String contenu) throws RemoteException {
		verifyCredentials(credentials);
		if(!fileManager.exists(name)) throw new FileNotFoundException(name);
		if(!locks.get(name).equals(credentials))
			return new PushResponse(name, false);
		//TODO: write the file
		// Release the lock.
		locks.remove(name);
		return new PushResponse(name, true);
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
