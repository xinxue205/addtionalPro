package charset;

import java.io.UnsupportedEncodingException;

public class UnicodeTest {
	public static void main(String[] args) throws Exception {
		System.out.println(toUnicode(""));
	}
	
	public static String toUnicode(String bgkStr){
		return Integer.toHexString('ѡ');
	}
	


}
