package http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import B2BURL.SECURITY.urlDisturb;

/**
 * 地址?参数名=参数 方式，不支持本地的tomcat
* @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
* @author Shawn.wu
* @date 2014-9-17 下午5:58:59
* @Description
* @version 1.0 Shawn create
 */
public class URLConnect2{
	public static void main(String[] args) throws Exception {
		test1();
		//test();
	}
	
	public static void test1()  throws Exception {
		urlDisturb ud = new urlDisturb();
		String password = "051;33<;1105815=3?0502<81>07825;100<019:370=0107191302:41616824?31160103391602<5281;01:5271>816028280100252=02:6312=02<52:2>010016358242193<82442041824=394102=50<418135054801290848022320508148115501:<235<815<2:5=01<72>5=024;00000000000000000000000000000000051<32>90<060198250:01032=0:02<50;0:824?1=0?01:21<1181603?1402=11>1;01003<2001241;2102:6282202<220230100412801=90=2:8241103182431734824:234302<;1743814>084801:92:4=01=?1950815<17550192345501=824570195215:82352=5=0107235>8250185?0291000000000000000000000000";
		String strEncode = new String(ud.encode(password.getBytes()));
		String voucher = new String(ud.decode(strEncode.getBytes()));

		String strUrl = "http://128.128.97.32:8082/UserRealNameAuth?xml=<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
				//String strUrl = "http://localhost:8080/NewATMVH/UserRealNameAuth?xml=<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
				"<TX><MESSAGE_HEADER>01</MESSAGE_HEADER><MESSAGE_BODY>" +
				"<DATE>20140911</DATE><TIME>223344555</TIME><AUTHTYPE>3</AUTHTYPE>" +
				" <USERNAME>uasstest.co</USERNAME><VOUCHER>"+strEncode+"</VOUCHER><ISENCRYPT>1</ISENCRYPT>" +
				//"<USERNAME>userid</USERNAME><PASSWD>xxxxx</PASSWD><ISENCRYPT>0</ISENCRYPT>" +
				"<CHANCODE>01</CHANCODE><ORGCODE>uasscode</ORGCODE><DEVCODE>110000101151</DEVCODE>" +
				"<CLIENTIP>192.168.1.1</CLIENTIP></MESSAGE_BODY></TX>";
		System.out.println(strUrl);
		URL url = new URL(new String(strUrl.getBytes(),"UTF-8"));
		HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
		urlCon.setUseCaches(false);
		System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
		System.setProperty("sun.net.client.defaultReadTime ", "30000");
				
		InputStream in = urlCon.getInputStream();
		byte[] bytes =  new byte[urlCon.getContentLength()];
		in.read(bytes);
		String xmlString  = new String(bytes,"UTF-8");
		System.out.println(xmlString);
	}
	
	public static void test() throws Exception {
		//String strUrl = " http://128.128.97.32:8082/CheckUserServlet?" +
		String strUrl = " http://127.0.0.1:8080/CheckUserServlet?" +
				"xml=<?xml version=\"1.0\" encoding=\"UTF-8\" ?><TX><MESSAGE_HEADER>17</MESSAGE_HEADER><MESSAGE_BODY><USERID>iabssh2</USERID><MENUID>1102</MENUID><PARAM>20140326152950450</PARAM></MESSAGE_BODY></TX>";
		//String strUrl = "http://128.128.200.54:8080/NewATMVH/CheckUserServlet?xml=<?xmlversion=\"1.0\"encoding=\"UTF-8\"?><TX><MESSAGE_HEADER>17</MESSAGE_HEADER><MESSAGE_BODY><USERID>admin</USERID><MENUID>1102</MENUID><PARAM>20140311115109234</PARAM></MESSAGE_BODY></TX>";
		//String strUrl = "http://128.128.200.54:8080/NewATMVH/outSystemUserAuthorityInterface.jsp?xml=<?xmlversion=\"1.0\"encoding=\"UTF-8\"?><TX><MESSAGE_HEADER>17</MESSAGE_HEADER><MESSAGE_BODY><USERID>admin</USERID><MENUID>1102</MENUID><PARAM>20140311115109234</PARAM></MESSAGE_BODY></TX>";
		//String strUrl = "http://128.128.200.54:8100/NewATMVH/outSystemUserInfoCheckInterface.jsp?xml=<?xml version=\"1.0\" encoding=\"UTF-8\" ?><TX><MESSAGE_HEADER>17</MESSAGE_HEADER><MESSAGE_BODY><USERID>admin</USERID><MENUID>1102</MENUID><PARAM>20140311115109234</PARAM></MESSAGE_BODY></TX>";
		
		URL url = new URL(new String(strUrl.getBytes(),"UTF-8"));
		HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
		urlCon.setUseCaches(false);
		System.setProperty("sun.net.client.defaultConnectTimeout", "60000");
		System.setProperty("sun.net.client.defaultReadTime ", "60000");
		
		InputStream in = urlCon.getInputStream();
		byte[] bytes =  new byte[urlCon.getContentLength()];
		in.read(bytes);
		String xmlString  = new String(bytes,"UTF-8");
		System.out.println(xmlString);
		xmlString = xmlString.trim();
		SAXBuilder saxReader = new SAXBuilder();
		Document document = saxReader.build(new ByteArrayInputStream(xmlString.getBytes("UTF-8")));
		Element element = document.getRootElement().getChild("MESSAGE_BODY");
		if(element!=null){
			String repRes = element.getChildText("REP_COD").trim();
			System.out.println(repRes);
		}
		
		/*StringBuffer xmlString = new StringBuffer("");
		//byte[] b = new byte[2048];	
		Reader rd = new InputStreamReader(in,"UTF-8");
		int iCount = 0;
		while ((iCount = rd.read()) > 0) {
			xmlString.append((char) iCount);
		}
		in.close();
		rd.close();*/
		//logger.info("单点登录应答报文2：" + xmlString);
	}
}

