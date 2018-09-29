package shared.files;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import shared.files.MD5Checksum;

/**
 * Unit test on the MD5Checksum class.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public class MD5ChecksumTest {

	@Test
	public void checksumTest() {
		byte[] input1 = {10, 20, 30};
		byte[] input2 = {1, 2 , 3};
		MD5Checksum c1 = new MD5Checksum(input1);
		MD5Checksum c2 = new MD5Checksum(input1);
		MD5Checksum c3 = new MD5Checksum(input2);
		assertTrue(c1.equals(c2));
		assertFalse(c1.equals(c3));
	}


}
