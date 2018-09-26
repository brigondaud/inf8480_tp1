package shared.files;

import shared.auth.Credentials;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.omg.CORBA.Current;

import shared.auth.Credentials;

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
	 * Serialize a Map Object into a given name file
	 *
	 * @param fileName File's name to save Map object
	 * @param map The Map to save in File fileName
	 */
	public void serializeMap(String fileName, Map map) throws IOException {
		FileOutputStream fos = new FileOutputStream(buildFilePath(fileName));
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(map);
		oos.close();
		fos.close();
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
	 * Retrieve user credentials from a file where they have been stored after a "new" operation
	 *
	 * @return The user's credentials, or null if no authentication has been performed or if an exception happened
	 */
	public Credentials retrieveUserCredentials(String fileName) throws IOException {
		try {
			if (!this.exists(buildFilePath(fileName))) {
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

}
