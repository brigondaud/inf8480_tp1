package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.zip.Checksum;

import com.sun.xml.internal.ws.api.pipe.ThrowableContainerPropertySet;

import shared.files.FileManager;
import shared.server.FileServerInterface;

/**
 * The file server receives client RMI calls for manipulating the files,
 * and communicates with the authentication server to validate client requests.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public class FileServer implements FileServerInterface {
	
	private static final String FILE_PATH = "/files";
	
	public static void main(String[] args) throws IOException {
		FileServer fs = new FileServer();
		//TODO: recover the metadata from a possible crash before running the server ?
		fs.run();
	}
	
	public FileServer() throws IOException {
		String execDir = System.getProperty("user.dir");
		FileManager.getInstance().setWorkingDirectory(execDir + FileServer.FILE_PATH);
	}

	@Override
	public boolean create(String name) throws RemoteException {
		return FileManager.getInstance().create(name);
	}

	@Override
	public File[] list() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File[] syncLocalDirectory() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File get(String name, Checksum checksum) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean lock(String name, Checksum checksum) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean push(String name, String contenu) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
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
