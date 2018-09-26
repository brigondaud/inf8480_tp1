package shared.files;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.PrinterLocation;

import org.junit.jupiter.api.Test;

/**
 * Tests for the different FileManager operations.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
class FileManagerTest {
	
	private FileManager fileManager;
	
	public FileManagerTest() throws IOException {
		this.fileManager = new FileManager();
	}
	
	@Test
	void createFile() throws IOException {
		String fileName = "testFile.txt";
		fileManager.create(fileName);
		File testFile = new File(fileManager.buildFilePath(fileName));
		assertTrue(testFile.exists());
		testFile.delete();
	}
	
	@Test
	void createDirAndFile() throws IOException {
		fileManager.setWorkingDirectory("files");
		String fileName = "testFile.txt";
		fileManager.create(fileName);
		File testFile = new File(fileManager.buildFilePath(fileName));
		assertTrue(testFile.exists());
		testFile.delete();
	}

	@Test
	void checkExistence() throws IOException {
		String fileName = "testFile.txt";
		String unexistingFile = "falseFile.txt";
		fileManager.create(fileName);
		assertTrue(fileManager.exists(fileName));
		assertFalse(fileManager.exists(unexistingFile));
		File testFile = new File(fileManager.buildFilePath(fileName));
		testFile.delete();
	}

	@Test
	void serializeMap() throws IOException {
		// Create a fake HashMap and fill it with values
		Map<String, String> map = new HashMap<>();
		map.put("a", "alpha");
		map.put("b", "beta");
		fileManager.setWorkingDirectory("auth");
		String fileName = "testFile.map";
		fileManager.serializeMap(fileName, map);
		assertTrue(fileManager.exists(fileName));
		File testFile = new File(fileManager.buildFilePath(fileName));
		testFile.delete();
	}

	@Test
	void deserializeMap() throws IOException, ClassNotFoundException {
		// Create a fake HashMap and fill it with values
		Map<String, String> map = new HashMap<>();
		map.put("a", "alpha");
		map.put("b", "beta");
		fileManager.setWorkingDirectory("auth");
		String fileName = "testFile.map";
		fileManager.serializeMap(fileName, map);
		Map<String, String> deserializedMap = fileManager.deserializeMap(fileName);
		assertEquals(2, deserializedMap.size());
		assertTrue(deserializedMap.containsKey("a"));
		assertTrue(deserializedMap.containsValue("beta"));
		File testFile = new File(fileManager.buildFilePath(fileName));
		testFile.delete();
	}

	@Test
	void serializeShouldOverwrite() throws IOException, ClassNotFoundException {
		// Create a fake HashMap and fill it with values
		Map<String, String> map = new HashMap<>();
		map.put("a", "alpha");
		map.put("b", "beta");
		map.put("c", "gamma");
		map.put("d", "delta");
		fileManager.serializeMap("test.map", map);
		// Create a second map with only 2 values and serialize it in the same file
		Map<String, String> littleMap = new HashMap<>();
		littleMap.put("a", "alpha");
		littleMap.put("b", "beta");
		fileManager.serializeMap("test.map", littleMap);
		Map<String, String> deserializedMap = fileManager.deserializeMap("test.map");
		assertEquals(2, deserializedMap.size());
		assertTrue(deserializedMap.containsKey("a"));
		assertTrue(deserializedMap.containsValue("beta"));
		File testFile = new File(fileManager.buildFilePath("test.map"));
		testFile.delete();
	}
	
	@Test
	void testList() throws IOException {
		fileManager.setWorkingDirectory("bin/testFiles");
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

}
