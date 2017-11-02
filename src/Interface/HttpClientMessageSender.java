package Interface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;



/**
 * 
 * 这个例子用来测试报文的发送。
 * 报文发送事例代码，这里使用了MultiThreadedHttpConnectionManager 在发送大量数据时，可以保证资源的回收利用。
 * 
 * 
 */
public class HttpClientMessageSender {

    private HttpConnectionManager connectionManager;

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO 根据自己需要填写reqXml，这里的reqXml不是文件名，而是文件的内容
    	
       String reqXml = "FOR TEST";   
        //Init.init();

        HttpClientMessageSender messageSender = new HttpClientMessageSender();
        String fileName="D:\\IM1020.xml";        
        reqXml=messageSender.readXmlFile(fileName);
        System.out.println("发送的报文是:"+reqXml);
        String resultXml = messageSender.send(reqXml,
            "http://localhost:8085/innermanage_JBPM/EMPService/innermanageWebService");
        System.out.println("收到的报文是:"+resultXml);
//        System.out.println("校验签名的结果是:" + SignUtil.check(resultXml, KeyUtil.getMerchantPubKey()));

    }

    public String send(String reqXml, String postUrl) throws Exception {

        // 发送报文
        HttpClient httpClient = new HttpClient(connectionManager);
        
        PostMethod method = new PostMethod(postUrl);

        method.addRequestHeader("Content-Type", "text/xml; charset=utf-8");
        try {
            method.setRequestEntity(new StringRequestEntity(reqXml, null, "utf-8"));

            httpClient.executeMethod(method);

            // 获得返回报文
            String resXml = method.getResponseBodyAsString();

            return resXml;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Cartoon0009");
        } finally {
            method.releaseConnection();
        }
    }

    public String sendForPost(String parameterName,String parameterValue, String postUrl) throws Exception {

        // 发送报文
        HttpClient httpClient = new HttpClient(connectionManager);
        
        PostMethod method = new PostMethod(postUrl);

//        method.addRequestHeader("Content-Type", "text/xml; charset=utf-8");
        try {
            method.setParameter(parameterName, parameterValue);
            httpClient.executeMethod(method);

            // 获得返回报文
            String resXml = method.getResponseBodyAsString();

            return resXml;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Cartoon0009");
        } finally {
            method.releaseConnection();
        }
    }

    public HttpClientMessageSender() {
        super();

        // 创建一个线程安全的HTTP连接池
        connectionManager = new MultiThreadedHttpConnectionManager();

        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        // 连接建立超时
        params.setConnectionTimeout(10000);
        // 数据等待超时
        params.setSoTimeout(30000);
        // 默认每个Host最多10个连接
        params.setDefaultMaxConnectionsPerHost(10);
        // 最大连接数（所有Host加起来）
        params.setMaxTotalConnections(200);

        connectionManager.setParams(params);
    }
    
    public String readXmlFile(String fileName){ 
    	try {
			BufferedReader br= new BufferedReader(new FileReader(new File(fileName)));
			String readString=null;
			StringBuffer sb=new StringBuffer();
			while((readString=br.readLine())!=null){
				sb.append(readString);
			}
			br.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return null;
    }

}