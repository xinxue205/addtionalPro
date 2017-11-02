/**
 * 
 */
package encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author wxx
 * @date 2014-2-14 上午10:18:28
 * @version 1.0 wxx create
 * @CopyRight (c) 2014 广州南天电脑系统有限公司
 */
public class MD5Encrypter {
	public static void main(String[] args) {
		String s = "path=mcht_eterminal_ectip&userid=999990061&menuid=290101&param= 20131113172012111&ectip_ip=11.156.109.220:7001";
		String s1 = encryptString(s);
		System.out.println(s1);
	}
	
	static String encryptString(String orgStr){
		String encryptedStr = "";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(orgStr.getBytes());
			StringBuffer buf = new StringBuffer();
			int t = 0 ;
			for (int i = 0; i < bytes.length; i++) {
				t = bytes[i] & 255;
				if(t<16) buf.append("0");
				buf.append(Integer.toHexString(t));
			}
			encryptedStr = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptedStr;
	}
}

