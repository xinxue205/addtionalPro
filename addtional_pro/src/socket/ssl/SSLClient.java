/**
 * 
 */
package socket.ssl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

/**
 * @author wuxinxue
 * @time 2015-7-6 ÉÏÎç11:27:41
 * @copyright hnisi
 */
public class SSLClient {  
	  
    private static String CLIENT_KEY_STORE = "/Users/liweinan/projs/ssl/src/main/resources/META-INF/client_ks";  
    private static String CLIENT_KEY_STORE_PASSWORD = "456456";
  
    public static void main(String[] args) throws Exception {  
        // Set the key store to use for validating the server cert.  
        System.setProperty("javax.net.ssl.trustStore", CLIENT_KEY_STORE);  
          
        System.setProperty("javax.net.debug", "ssl,handshake");  
  
        SSLClient client = new SSLClient();  
//        Socket s = client.clientWithoutCert();  
        Socket s = client.clientWithCert();
  
        PrintWriter writer = new PrintWriter(s.getOutputStream());  
        BufferedReader reader = new BufferedReader(new InputStreamReader(s  
                .getInputStream()));  
        writer.println("hello");  
        writer.flush();  
        System.out.println(reader.readLine());  
        s.close();  
    }  
  
    private Socket clientWithoutCert() throws Exception {  
        SocketFactory sf = SSLSocketFactory.getDefault();  
        Socket s = sf.createSocket("localhost", 8443);  
        return s;  
    }
    
    private Socket clientWithCert() throws Exception {  
        SSLContext context = SSLContext.getInstance("TLS");  
        KeyStore ks = KeyStore.getInstance("jceks");  
          
        ks.load(new FileInputStream(CLIENT_KEY_STORE), null);  
        KeyManagerFactory kf = KeyManagerFactory.getInstance("SunX509");  
        kf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());  
        context.init(kf.getKeyManagers(), null, null);  
          
        SocketFactory factory = context.getSocketFactory();  
        Socket s = factory.createSocket("localhost", 8443);  
        return s;  
    }
} 
