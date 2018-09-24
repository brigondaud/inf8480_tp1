package shared.files;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

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

}
