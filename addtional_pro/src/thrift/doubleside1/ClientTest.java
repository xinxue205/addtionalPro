package thrift.doubleside1;

import java.io.IOException;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
 
public class ClientTest {
 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TSocket tSocket = new TSocket("localhost",9999);
		CommunicateService.Client client = new CommunicateService.Client(new TBinaryProtocol(tSocket));
		try {
			tSocket.open();
			runMethod(tSocket);
			//向服务端发送消息
			for (int i = 0; i < 100; ++i){
	            client.say(i+" "+i+" "+i);
	            Thread.sleep(1000);
	        }
			System.in.read();
			tSocket.close();
		} catch (TTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
	public static void runMethod(final TSocket tSocket){
		Thread thread = new Thread(new Runnable(){
			CommunicateServiceImpl imp = new CommunicateServiceImpl(tSocket);
			@Override
			public void run() {
				// TODO Auto-generated method stub
				imp.process();
			}
			
		});
		thread.start();
	};
}
