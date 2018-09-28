package shared.files;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.Checksum;

import javax.xml.bind.DatatypeConverter;

import shared.auth.Credentials;

/**
 * Represents a MD5 checksum computed in the file server to
 * avoid not necessary file transfers.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public class MD5Checksum implements Checksum {
	
	/**
	 * Checksum's value.
	 */
	private long value;
	
	/**
	 * The MD5 checksum is computed thanks to a byte array.
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public MD5Checksum(byte[] input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input);
			byte[] digest = md.digest();
			this.value = DatatypeConverter.parseLong(DatatypeConverter.printHexBinary(digest));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public long getValue() {
		return this.value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof Checksum)) return false;
		return value == ((Checksum)obj).getValue();
	}
	
	/**
	 * Code smell: Not implemented since not needed.
	 */
	@Override
	public void reset() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Code smell: Not implemented since not needed.
	 */
	@Override
	public void update(int b) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Code smell: Not implemented since not needed.
	 */
	@Override
	public void update(byte[] b, int off, int len) {
		throw new UnsupportedOperationException();
	}

}
