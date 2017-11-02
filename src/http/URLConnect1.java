package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HttpURLConnection方式链接，xml报文直接以流发送
* @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
* @author Shawn.wu
* @date 2014-9-17 下午5:58:59
* @Description
* @version 1.0 Shawn create
 */
public class URLConnect1{
	public static void main(String[] args) throws IOException  {
		test1();
		//test();
	}
	
	public static void test1(){
		String sUrl = "http://localhost:8080/UserRealNameAuth?xml=";
		String sSendContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
				"<TX><MESSAGE_HEADER>01</MESSAGE_HEADER><MESSAGE_BODY>" +
				"<USERNAME>userid</USERNAME>" +
				"<PASSWD>xxxxxxxxx</PASSWD>" +
				"<ISENCRYPT>0</ISENCRYPT>" +
				"<CLIENTIP>192.168.1.1</CLIENT>" +
				"<TIME>20140904112233444</TIME>" +
				"</MESSAGE_BODY></TX>";
		String sReturnMsg = "";
		  URL url = null;
		  HttpURLConnection httpurlconnection = null;
		  try {

		   url = new URL(sUrl);

		   // 以post方式请求
		   httpurlconnection = (HttpURLConnection) url.openConnection();
		   httpurlconnection.setConnectTimeout(30000);
		   httpurlconnection.setReadTimeout(30000);
		   httpurlconnection.setDoOutput(true);
		   httpurlconnection.setRequestMethod("POST");
		   
		   httpurlconnection.setInstanceFollowRedirects(true);
		   httpurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		   httpurlconnection.setDefaultUseCaches(false);
		   httpurlconnection.setDoOutput(true);

			PrintWriter out = new PrintWriter(httpurlconnection.getOutputStream());
			out.print(sSendContent);// 传入参数
			out.close();

//		   httpurlconnection.getOutputStream().write( sSendContent.getBytes("utf-8"));
//		   httpurlconnection.getOutputStream().flush();
//		   httpurlconnection.getOutputStream().close();
		   // 获取页面内容
		   InputStream in = httpurlconnection.getInputStream();
		   BufferedReader breader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		   String str = breader.readLine();
		  
		   while (str != null) {
			   sReturnMsg+=str;
		       str= breader.readLine();
		   }
		   in.close();
		   breader.close();
		  } catch (Exception e) {
			  e.printStackTrace();
		  } finally {
		       if (httpurlconnection != null){
		         httpurlconnection.disconnect();
		         httpurlconnection = null;
		       }
		  }
		 System.out.println(sReturnMsg);
	}
	
	public static void test() throws IOException{
		String remoterurl = "http://localhost:8080/CheckUserServlet";
		String strReq = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><TX><MESSAGE_HEADER>17</MESSAGE_HEADER><MESSAGE_BODY><USERID>admin</USERID><MENUID>1102</MENUID></MESSAGE_BODY></TX>";
		
		strReq = strReq.replaceAll("\n", "");
		strReq = strReq.replaceAll("  ", "");

		String strUrl = remoterurl;
		
		URL url = new URL(strUrl);
		HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
		urlCon.setUseCaches(false);
		System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
		System.setProperty("sun.net.client.defaultReadTime ", "30000");
		
		url.openConnection();
		//urlCon.setRequestMethod("POST");
		urlCon.setDoOutput(true);
//		urlCon.connect();
		OutputStream os = urlCon.getOutputStream();
		os.write(strReq.getBytes("utf-8"));
		urlCon.connect();
		os.close();
		
		InputStream in = urlCon.getInputStream();
		StringBuffer xmlString = new StringBuffer("");
//		byte[] b = new byte[2048];
		Reader rd = new InputStreamReader(in,"UTF-8");
		int iCount = 0;
		while ((iCount = rd.read()) > 0) {
			xmlString.append((char) iCount);
		}
		in.close();
		rd.close();
		//logger.info("单点登录应答报文2：" + xmlString);
		System.out.println(xmlString);
	}
}

