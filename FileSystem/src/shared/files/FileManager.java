package shared.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	 * Singleton pattern: the file manager can only be accessed through one reference
	 * to avoid concurrent access to files.
	 */
	public static FileManager instance = null;
	
	/**
	 * Used to set the directory on which the file manager will operate.
	 */
	private Path workingDirectory;
	
	private FileManager() throws IOException {
		// Default working directory: the user working directory.
		setWorkingDirectory(System.getProperty("user.dir"));
	}
	
	/**
	 * Singleton: get the instance of the file manager.
	 * 
	 * @return
	 */
	public static final FileManager getInstance() throws IOException {
		if(instance == null) {
			instance = new FileManager();
		}
		return instance;
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
	 * Builds a path with the given file name based on the current
	 * working directory.
	 * @param fileName The file for which the path must be built.
	 * @return
	 */
	private String buildFilePath(String fileName) {
		return this.workingDirectory + System.getProperty("file.separator") + fileName;
	}
	
}
