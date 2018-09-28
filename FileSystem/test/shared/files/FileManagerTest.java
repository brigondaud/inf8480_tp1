package shared.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import shared.auth.Credentials;

import static org.junit.Assert.*;

/**
 * Tests for the different FileManager operations.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public class FileManagerTest {
	
	private FileManager fileManager;

	public FileManagerTest() throws IOException {
		this.fileManager = new FileManager();
	}

	@Test
	public void createFile() throws IOException {
		fileManager.setWorkingDirectory("bin/tests/createFile");
		String fileName = "testFile.txt";
		fileManager.create(fileName);
		File testFile = new File(fileManager.buildFilePath(fileName));
		assertTrue(testFile.exists());
		testFile.delete();
	}
	
	@Test
	public void createDirAndFile() throws IOException {
		fileManager.setWorkingDirectory("files");
		String fileName = "testFile.txt";
		fileManager.create(fileName);
		File testFile = new File(fileManager.buildFilePath(fileName));
		assertTrue(testFile.exists());
		testFile.delete();
	}

	@Test
	public void checkExistence() throws IOException {
		fileManager.setWorkingDirectory("bin/tests/checkExistence");
		String fileName = "testFile.txt";
		String unexistingFile = "falseFile.txt";
		fileManager.create(fileName);
		assertTrue(fileManager.exists(fileName));
		assertFalse(fileManager.exists(unexistingFile));
		File testFile = new File(fileManager.buildFilePath(fileName));
		testFile.delete();
	}

	@Test
	public void serializeMap() throws IOException {
		fileManager.setWorkingDirectory("bin/tests/serialize");
		// Create a fake HashMap and fill it with values
		Map<String, String> map = new HashMap<>();
		map.put("a", "alpha");
		map.put("b", "beta");
		String fileName = "testFile.map";
		fileManager.write(fileName, map);
		assertTrue(fileManager.exists(fileName));
		File testFile = new File(fileManager.buildFilePath(fileName));
		testFile.delete();
	}

	@Test
	public void serializeCredentials() throws IOException {
		fileManager.setWorkingDirectory("bin/tests/serialize");
		String fileName = "testFile.credentials";
		Credentials testCredentials = new Credentials("loginTest", "passwordTest");
		fileManager.write(fileName, testCredentials);
		assertTrue(fileManager.exists(fileName));
		File testFile = new File(fileManager.buildFilePath(fileName));
		testFile.delete();
	}

	@Test
	public void write() throws IOException {
		byte[] testBytes = { 65, 66, 67, 68, 69};
		fileManager.setWorkingDirectory("bin/tests/serialize");
		String fileName = "testFile.bytes";
		fileManager.write(fileName, testBytes);
		byte[] writtenBytes = Files.readAllBytes(Paths.get(fileManager.buildFilePath(fileName)));
		assertArrayEquals(testBytes, writtenBytes);
		File testFile = new File(fileManager.buildFilePath(fileName));
		testFile.delete();
	}

	@Test
	public void deserialize() throws IOException, ClassNotFoundException {
		fileManager.setWorkingDirectory("bin/tests/deserialize");
		// Create a fake HashMap and fill it with values
		Map<String, String> map = new HashMap<>();
		map.put("a", "alpha");
		map.put("b", "beta");
		String fileName = "testFile.map";
		fileManager.write(fileName, map);
		Map<String, String> deserializedMap = fileManager.deserializeMap(fileName);
		assertEquals(2, deserializedMap.size());
		assertTrue(deserializedMap.containsKey("a"));
		assertTrue(deserializedMap.containsValue("beta"));
		File testFile = new File(fileManager.buildFilePath(fileName));
		testFile.delete();
	}

	@Test
	public void serializeShouldOverwrite() throws IOException, ClassNotFoundException {
		fileManager.setWorkingDirectory("bin/tests/serializeShouldOverwrite");
		// Create a fake HashMap and fill it with values
		Map<String, String> map = new HashMap<>();
		map.put("a", "alpha");
		map.put("b", "beta");
		map.put("c", "gamma");
		map.put("d", "delta");
		fileManager.write("test.map", map);
		// Create a second map with only 2 values and serialize it in the same file
		Map<String, String> littleMap = new HashMap<>();
		littleMap.put("a", "alpha");
		littleMap.put("b", "beta");
		fileManager.write("test.map", littleMap);
		Map<String, String> deserializedMap = fileManager.deserializeMap("test.map");
		assertEquals(2, deserializedMap.size());
		assertTrue(deserializedMap.containsKey("a"));
		assertTrue(deserializedMap.containsValue("beta"));
		File testFile = new File(fileManager.buildFilePath("test.map"));
		testFile.delete();
	}
	
	@Test
	public void testList() throws IOException {
		fileManager.setWorkingDirectory("bin/tests/testList");
		String[] files = {"1", "2", "3"};
		for(String name: files) {
			fileManager.create(name);
		}
		String[] filesFound = fileManager.list();
		assertArrayEquals(files, filesFound);
		for(String name: files) {
			File file = new File(fileManager.buildFilePath(name));
			file.delete();
		}
	}
	
	@Test
	public void readTest() throws IOException {
		fileManager.setWorkingDirectory("bin/tests/readTest");
		String content = "Read test, should find this content!";
		//TODO
	}

}
