package shared.files;

import shared.auth.Credentials;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * The FileManager is used to access files, for reading, writing and
 * accessing files properties. All the operations are performed in a working
 * directory that can be set at any time.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public class FileManager {
	
	/**
	 * The path to the client files.
	 */
	public static final String CLIENT_FILES_PATH = "clientFiles";
	
	/**
	 * The path to the server files.
	 */
	public static final String SERVER_FILES_PATH = "serverFiles";
	
	public static final String AUTH_FILES_PATH = "authFiles";
	
	/**
	 * Used to set the directory on which the file manager will operate.
	 */
	private Path workingDirectory;
	
	public FileManager() throws IOException {
		// Default working directory: the user working directory.
		setWorkingDirectory(System.getProperty("user.dir"));
	}
	
	/**
	 * Sets the working directory according to the given path. If the
	 * directory does not exists, it is created.
	 * 
	 * @param path The path to the working directory to set.
	 * @throws IOException 
	 */
	public void setWorkingDirectory(String path) throws IOException {
		this.workingDirectory = Paths.get(path);
		if(Files.exists(this.workingDirectory)) return;
		Files.createDirectories(this.workingDirectory);
	}
	
	/**
	 * Creates a file in the working directory given the name. If the file already
	 * exists, the file is not overwritten.
	 * 
	 * @param name File's name to create.
	 * @return True if the file has successfully been created.
	 */
	public boolean create(String name) throws IOException {
		
		File file = new File(buildFilePath(name));
		return file.createNewFile();
	}
	
	/**
	 * Get the list of the files in the current working directory.
	 * 
	 * @return A list of file.
	 */
	public String[] list() {
		//TODO: give also the locks !
		return new File(workingDirectory.toString()).list();
	}
	
	/**
	 * Reads the file with the given filename.
	 * 
	 * @param fileName The file to read.
	 * @return A byte array corresponding to the file content.
	 * @throws IOException if the file is not found.
	 */
	public byte[] read(String fileName) throws IOException {
		if(!exists(fileName)) throw new IOException("Cannot read not existing file: " + fileName);
		return Files.readAllBytes(Paths.get(buildFilePath(fileName)));
	}

	/**
	 * Check if a given file name exists in the current working directory
	 *
	 * @param name File's name to check
	 * @return True if the file exists, False otherwise
	 */
	public boolean exists(String name) {
		return new File(buildFilePath(name)).exists();
	}
	
	/**
	 * Builds a path with the given file name based on the current
	 * working directory.
	 * 
	 * @param fileName The file for which the path must be built.
	 * @return The path to the given file name based on the working directory.
	 */
	public String buildFilePath(String fileName) {
		return this.workingDirectory + System.getProperty("file.separator") + fileName;
	}
	
	/**
	 * Computes the MD5 checksum of the given file.
	 * 
	 * @param fileName The file to compute the checksum on.
	 * @return MD5 checksum.
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */
	public MD5Checksum checksum(String fileName) {
		try {
			return new MD5Checksum(read(fileName));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	/**
	 * Serialize an Object into a given name file
	 *
	 * @param fileName File's name to save Map object
	 * @param object The object to serialize in File fileName
	 */
	public void write(String fileName, Object object) throws IOException {
		FileOutputStream fos = new FileOutputStream(buildFilePath(fileName));
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(object);
		oos.close();
		fos.close();
	}

	/**
	 * Serialize a byte array into a given name file
	 *
	 * @param fileName File's name to save Map object
	 * @param array the array to write in File fileName
	 */
	public void write(String fileName, byte[] array) throws IOException {
	    Files.write(Paths.get(buildFilePath(fileName)), array);
	}
	
	/**
	 * Write a file with its content in the current working directory.
	 * 
	 * @param fileName The file to write in.
	 * @param content The file content to write.
	 * @throws IOException
	 */
	public void write(String fileName, String content) throws IOException {
		this.write(fileName, content.getBytes());
	}

	/**
	 * Deserialize a given file content into a Map
	 *
	 * @param fileName File's name to deserialize
	 * @return The de-serialized Map Object
	 */
	public Map<String, String> deserializeMap(String fileName) throws IOException, ClassNotFoundException {
		FileInputStream fis;
		fis = new FileInputStream(buildFilePath(fileName));
		ObjectInputStream ois = new ObjectInputStream(fis);
		HashMap result = (HashMap) ois.readObject();
		ois.close();
		fis.close();
		return result;
	}

	/**
	 * Deserialize a given file content into a Map
	 *
	 * @param fileName File's name to deserialize
	 * @return The de-serialized Credentials Object
	 */
	public Credentials deserializeCredentials(String fileName) throws IOException, ClassNotFoundException {
		FileInputStream fis;
		fis = new FileInputStream(buildFilePath(fileName));
		ObjectInputStream ois = new ObjectInputStream(fis);
		Credentials result = (Credentials) ois.readObject();
		ois.close();
		fis.close();
		return result;
	}
	
	/**
	 * Retrieves file server metadata.
	 *
	 * @param fileName File's name to deserialize
	 * @return The de-serialized Map Object
	 */
	public Map<String, Credentials> retrieveServerLocks(String fileName) throws IOException {
		FileInputStream fis;
		fis = new FileInputStream(buildFilePath(fileName));
		ObjectInputStream ois = new ObjectInputStream(fis);
		HashMap<String, Credentials> result;
		try {
			result = (HashMap<String, Credentials>) ois.readObject();
		} catch (ClassNotFoundException c) {
			System.err.println("Locks hashmap type not found");
			c.printStackTrace();
			ois.close();
			fis.close();
			return null;
		}
		ois.close();
		fis.close();
		return result;
	}

	/**
	 * Retrieve user credentials from a file where they have been stored after a "new" operation
	 *
	 * @return The user's credentials, or null if no authentication has been performed or if an exception happened
	 */
	public Credentials retrieveUserCredentials(String fileName) throws IOException {
		try {
			if (!this.exists(fileName)) {
				return null;
			}
			FileInputStream fis = new FileInputStream(buildFilePath(fileName));
			ObjectInputStream ois = new ObjectInputStream(fis);
			Credentials credentials = (Credentials) ois.readObject();
			ois.close();
			fis.close();
			return credentials;
		} catch (ClassNotFoundException c) {
			System.err.println("Credentials class not found");
			c.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Creates a file manager ready to operate on the client side file system.
	 * 
	 * @return A client side file manager.
	 */
	public static FileManager createClientManager() {
		try {
			FileManager fm = new FileManager();
			fm.setWorkingDirectory(System.getProperty("user.dir") 
					+ System.getProperty("file.separator") 
					+ FileManager.CLIENT_FILES_PATH);
			return fm;
		} catch (IOException e) {
			System.err.println("Cannot create client ready file manager.");
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	/**
	 * Creates a file manager ready to operate on the server side file system.
	 * 
	 * @return A server side file manager.
	 */
	public static FileManager createServerManager() {
		try {
			FileManager fm = new FileManager();
			fm.setWorkingDirectory(System.getProperty("user.dir") 
					+ System.getProperty("file.separator") 
					+ FileManager.SERVER_FILES_PATH);
			return fm;
		} catch (IOException e) {
			System.err.println("Cannot create server ready file manager.");
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	/**
	 * Creates a file manager ready to operate on the auth side file system.
	 * 
	 * @return An auth side file manager.
	 */
	public static FileManager createAuthenticationManager() {
		try {
			FileManager fm = new FileManager();
			fm.setWorkingDirectory(System.getProperty("user.dir") 
					+ System.getProperty("file.separator") 
					+ FileManager.AUTH_FILES_PATH);
			return fm;
		} catch (IOException e) {
			System.err.println("Cannot create auth ready file manager.");
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

}
