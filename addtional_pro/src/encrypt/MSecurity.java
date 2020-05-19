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
public class MSecurity {
	static urlDisturb ud = new urlDisturb();
	
	public static void main(String[] args) {
		String str = "path=reportShow/screen/report/infoQuery/etbJournalQuery.jsp&userid=etbId&menuid=1106&param=20140217172012111&etb_ip=11.156.109.220:7001";
		String encodedStr = encodeStr(str);
		System.out.println(encodedStr);
		String decodedStr = decodeStr(encodedStr);
		System.out.println(decodedStr);

		String paramsArr[] = decodedStr.split("&");
		String accessPath = paramsArr[0].split("=")[1];
		String userId = paramsArr[1].split("=")[1];
		String menuId = paramsArr[2].split("=")[1];
		String timeStamp = paramsArr[3].split("=")[1];
		String accessIP = paramsArr[4].split("=")[1];
		System.out.println("外系统开始访问，信息: "+accessPath+"--"+userId+"--"+menuId+"--"+timeStamp+"--"+accessIP);
	}
	
	static String encodeStr(String str){
		String strEncode = new String(ud.encode(str.getBytes()));
		String strURLEncode = URLEncoder.encode(strEncode);
		//System.out.println(strURLEncode);
		return strURLEncode;
	}
	
	static String decodeStr(String str){
		String strURLDecode= URLDecoder.decode(str);
		String decodeStr = new String(ud.decode(strURLDecode.getBytes()));
		//System.out.println(decodeStr);
		return decodeStr;
	}
	
}
