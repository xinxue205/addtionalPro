/**
 * 
 */
package encrypt;

import java.net.URLDecoder;
import java.net.URLEncoder;
import B2BURL.SECURITY.urlDisturb;

/**
 *
 * @author wxx
 * @date 2014-2-19 下午3:56:40
 * @version 1.0 wxx create
 * @CopyRight (c) 2014 广州南天电脑系统有限公司
 */
public class MSecurity1 {
	static urlDisturb ud = new urlDisturb();
	
	public static void main(String[] args) {
		String str = "admin";
		String encodedStr = encodeStr(str);
		System.out.println(encodedStr);
		String decodedStr = decodeStr(encodedStr);
		System.out.println(decodedStr);
	}
	
	static String encodeStr(String str){
		String strEncode = new String(ud.encode(str.getBytes()));
		//String strURLEncode = URLEncoder.encode(strEncode);
		return strEncode;
	}
	
	static String decodeStr(String str){
		String strURLDecode= URLDecoder.decode(str);
		String decodeStr = new String(ud.decode(strURLDecode.getBytes()));
		//System.out.println(decodeStr);
		return decodeStr;
	}
	
}
