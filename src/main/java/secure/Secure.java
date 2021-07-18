package secure;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Secure {
	public static String sha256(String str) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
	    md.update(str.getBytes());
		
		return bytes2str(md.digest());
	}
	
	private static String bytes2str(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
	    for (byte b: bytes) {
	      builder.append(String.format("%02x", b));
	    }
	    return builder.toString();
	}
}
