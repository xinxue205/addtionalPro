package http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 *  * URLConnection方式链接，xml报文直接以流发送
* @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
* @author Shawn.wu
* @date 2014-9-24 下午8:30:04
* @Description
* @version 1.0 Shawn create
 */
public class URLConnect {
    
    public static void main(String[] args) throws Exception{
		//test();
    	test1();
	}
    
    public static void test1() throws Exception{
    	
    }
    
    public static void test() throws Exception{
    	String returnStr = "-1";
        //String sMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><TransCode>TestComm</TransCode><DeviceNo>123456789012</DeviceNo><SystemDate>20100323</SystemDate><SystemTime>121212</SystemTime></root>";
    	String sMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><TX><MESSAGE_HEADER>01</MESSAGE_HEADER><MESSAGE_BODY><USERID>admin</USERID><MENUID>好好</MENUID><PARAM>20140310154231664</PARAM></MESSAGE_BODY></TX>";
    	
        String urlStr = "http://128.128.200.54:8080/NewATMVH/outSystemUserInfoCheckInterface.jsp";        
        //String urlStr = "http://" + SysParam.getHttpParam("vhIp")+":" + SysParam.getHttpParam("vhPort") + "/" + SysParam.getHttpParam("vhProjectName") + "/ReceiverServlet/";        
//        String urlStr = "http://128.128.81.125:6003/clear/ReceiverServlet/";
//		returnMessage.add("VHSystemTime", Str.getNowTime());
//		returnMessage.add("UrlServerIP", SysParam.getHttpParam("vhIp"));
//		returnMessage.add("UrlServerPort", SysParam.getHttpParam("vhPort"));
//		returnMessage.add("UrlServerPath","/" + SysParam.getHttpParam("vhProjectName") + "/upload_http_file/urlListFile");
        //http://11.157.66.64:1381/clear/ReceiverServlet/
        //http://11.157.66.64:1381/clear//ReceiverServlet/
        URL url = new URL(urlStr);
        
        URLConnection con = url.openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestProperty("Content-type", "application/octest-stream");
        con.setRequestProperty("Method", "POST HTTP/1.1");
                
        OutputStream out = con.getOutputStream();
        BufferedWriter objStream = new BufferedWriter(new OutputStreamWriter(out));
        objStream.write(sMessage);
        objStream.flush();
        objStream.close();
        out.close();
                
        InputStream in = con.getInputStream();        
        BufferedReader back = new BufferedReader(new InputStreamReader(in));
        
        returnStr = (String)back.readLine();
        back.close();
        in.close();
        System.out.println(returnStr);
    }
}
