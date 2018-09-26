package shared.files;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
	
	@Test
	void createFile() throws IOException {
		String fileName = "testFile.txt";
		FileManager.getInstance().create(fileName);
		File testFile = new File(FileManager.getInstance().buildFilePath(fileName));
		assertTrue(testFile.exists());
		testFile.delete();
	}
	
	@Test
	void createDirAndFile() throws IOException {
		FileManager.getInstance().setWorkingDirectory("files");
		String fileName = "testFile.txt";
		FileManager.getInstance().create(fileName);
		File testFile = new File(FileManager.getInstance().buildFilePath(fileName));
		assertTrue(testFile.exists());
		testFile.delete();
	}

	@Test
	void checkExistence() throws IOException {
		String fileName = "testFile.txt";
		String unexistingFile = "falseFile.txt";
		FileManager.getInstance().create(fileName);
		assertTrue(FileManager.getInstance().exists(fileName));
		assertFalse(FileManager.getInstance().exists(unexistingFile));
		File testFile = new File(FileManager.getInstance().buildFilePath(fileName));
		testFile.delete();
	}

	@Test
	void serializeMap() throws IOException {
		// Create a fake HashMap and fill it with values
		Map<String, String> map = new HashMap<>();
		map.put("a", "alpha");
		map.put("b", "beta");
		FileManager.getInstance().setWorkingDirectory("auth");
		String fileName = "testFile.map";
		FileManager.getInstance().serializeMap(fileName, map);
		assertTrue(FileManager.getInstance().exists(fileName));
		File testFile = new File(FileManager.getInstance().buildFilePath(fileName));
		testFile.delete();
	}

	@Test
	void deserializeMap() throws IOException, ClassNotFoundException {
		// Create a fake HashMap and fill it with values
		Map<String, String> map = new HashMap<>();
		map.put("a", "alpha");
		map.put("b", "beta");
		FileManager.getInstance().setWorkingDirectory("auth");
		String fileName = "testFile.map";
		FileManager.getInstance().serializeMap(fileName, map);
		Map<String, String> deserializedMap = FileManager.getInstance().deserializeMap(fileName);
		assertEquals(2, deserializedMap.size());
		assertTrue(deserializedMap.containsKey("a"));
		assertTrue(deserializedMap.containsValue("beta"));
		File testFile = new File(FileManager.getInstance().buildFilePath(fileName));
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
		FileManager.getInstance().serializeMap("test.map", map);
		// Create a second map with only 2 values and serialize it in the same file
		Map<String, String> littleMap = new HashMap<>();
		littleMap.put("a", "alpha");
		littleMap.put("b", "beta");
		FileManager.getInstance().serializeMap("test.map", littleMap);
		Map<String, String> deserializedMap = FileManager.getInstance().deserializeMap("test.map");
		assertEquals(2, deserializedMap.size());
		assertTrue(deserializedMap.containsKey("a"));
		assertTrue(deserializedMap.containsValue("beta"));
		File testFile = new File(FileManager.getInstance().buildFilePath("test.map"));
		testFile.delete();
	}

}
