package shared.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
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
		assertEquals(c1, c2);
		assertNotEquals(c1, c3);
	}


}
