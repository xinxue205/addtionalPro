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
 * ��������������Ա��ĵķ��͡�
 * ���ķ����������룬����ʹ����MultiThreadedHttpConnectionManager �ڷ��ʹ�������ʱ�����Ա�֤��Դ�Ļ������á�
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
        // TODO �����Լ���Ҫ��дreqXml�������reqXml�����ļ����������ļ�������
    	
       String reqXml = "FOR TEST";   
        //Init.init();

        HttpClientMessageSender messageSender = new HttpClientMessageSender();
        String fileName="D:\\IM1020.xml";        
        reqXml=messageSender.readXmlFile(fileName);
        System.out.println("���͵ı�����:"+reqXml);
        String resultXml = messageSender.send(reqXml,
            "http://localhost:8085/innermanage_JBPM/EMPService/innermanageWebService");
        System.out.println("�յ��ı�����:"+resultXml);
//        System.out.println("У��ǩ���Ľ����:" + SignUtil.check(resultXml, KeyUtil.getMerchantPubKey()));

    }

    public String send(String reqXml, String postUrl) throws Exception {

        // ���ͱ���
        HttpClient httpClient = new HttpClient(connectionManager);
        
        PostMethod method = new PostMethod(postUrl);

        method.addRequestHeader("Content-Type", "text/xml; charset=utf-8");
        try {
            method.setRequestEntity(new StringRequestEntity(reqXml, null, "utf-8"));

            httpClient.executeMethod(method);

            // ��÷��ر���
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

        // ���ͱ���
        HttpClient httpClient = new HttpClient(connectionManager);
        
        PostMethod method = new PostMethod(postUrl);

//        method.addRequestHeader("Content-Type", "text/xml; charset=utf-8");
        try {
            method.setParameter(parameterName, parameterValue);
            httpClient.executeMethod(method);

            // ��÷��ر���
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

        // ����һ���̰߳�ȫ��HTTP���ӳ�
        connectionManager = new MultiThreadedHttpConnectionManager();

        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        // ���ӽ�����ʱ
        params.setConnectionTimeout(10000);
        // ���ݵȴ���ʱ
        params.setSoTimeout(30000);
        // Ĭ��ÿ��Host���10������
        params.setDefaultMaxConnectionsPerHost(10);
        // ���������������Host��������
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