package shared.files;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import shared.auth.Credentials;

/**
 * Represents a MD5 checksum computed in the file server to
 * avoid not necessary file transfers.
 * 
 * @author Loic Poncet & Baptiste Rigondaud
 *
 */
public class MD5Checksum implements Serializable {

	/**
	 * Checksum's value.
	 */
	private String value;
	
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
			this.value = DatatypeConverter.printHexBinary(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Getter for MD5Checksum's value.
	 * 
	 * @return MD5Checksum's value.
	 */
	public String getValue() {
		return this.value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof MD5Checksum)) return false;
		return value.equals(((MD5Checksum)obj).getValue());
	}

}
