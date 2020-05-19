package MD5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Test {
	
	public static void main(String[] args) {
		System.out.println(DigestUtils.md5Hex("750302318220171124110000FH7BQ016553302219800418162120171124104027"));
		System.out.println(DigestUtils.md5Hex("750302318220171124110000FH7BQ016653302220000102162220171124104027"));
//		Md5("750302318220171124110000FH7BQ016553302219800418162120171124104027");
//		Md5("12312312312312");
	}
	
	private static void Md5(String plainText ) {
		try { 
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(plainText.getBytes()); 
			byte b[] = md.digest(); 
			int i; 
			StringBuffer buf = new StringBuffer(""); 
			for (int offset = 0; offset < b.length; offset++) { 
				i = b[offset]; 
				if(i<0) i+= 256; 
				if(i<16) buf.append("0"); 
				buf.append(Integer.toHexString(i)); 
			} 
			System.out.println("result: " + buf.toString());//32位的加密 
			System.out.println("result: " + buf.toString().substring(8,24));//16位的加密 
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace(); 
		} 
	}
}
