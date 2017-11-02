package http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPClientConnect1{
	public static void main(String[] args) throws IOException  {
		test();
	}
	
	public static void test()throws IOException{
		String strUrl = "http://hq.sinajs.cn/list=sh000001,sz300479";
//		String strUrl = "http://hq.sinajs.cn/list=sh000001,sz399006,sh600405,sz300011,sz300469,sh603118";
//		String strUrl = "http://hq.sinajs.cn/rn=61fdj&format=text&list=stock_sh_up_5min_20";

		//String strUrl = "http://128.128.200.54:8080/NewATMVH/outSystemUserAuthorityInterface.jsp?xml=<?xmlversion=\"1.0\"encoding=\"UTF-8\"?><TX><MESSAGE_HEADER>17</MESSAGE_HEADER><MESSAGE_BODY><USERID>admin</USERID><MENUID>1102</MENUID><PARAM>20140311115109234</PARAM></MESSAGE_BODY></TX>";
		//String strUrl = "http://128.128.97.35:6063/outSystemUserInfoCheckInterface.jsp?xml=<?xml version=\"1.0\" encoding=\"UTF-8\" ?><TX><MESSAGE_HEADER>17</MESSAGE_HEADER><MESSAGE_BODY><USERID>admin</USERID><MENUID>1102</MENUID><PARAM>20140311115109234</PARAM></MESSAGE_BODY></TX>";
		
		URL url = new URL(strUrl);
		HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
		urlCon.setUseCaches(false);
		System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
		System.setProperty("sun.net.client.defaultReadTime ", "30000");
		
		InputStream in = urlCon.getInputStream();
		StringBuffer xmlString = new StringBuffer("");
		//byte[] b = new byte[2048];
		Reader rd = new InputStreamReader(in,"GBK");
		int iCount = 0;
		while ((iCount = rd.read()) > 0) {
			xmlString.append((char) iCount);
		}
		in.close();
		rd.close();
		//logger.info("单点登录应答报文2：" + xmlString);
		String infos[] = xmlString.toString().split(System.getProperty("line.separator"));
		
		for (int i = 0; i < infos.length; i++) {
			System.out.println(i+" is "+infos[i]);
		}
	}
}

