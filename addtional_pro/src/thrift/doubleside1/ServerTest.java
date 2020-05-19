package thrift.doubleside1;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
 
public class ServerTest {
 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TServerSocket tServerSocket;
		try {
			tServerSocket = new TServerSocket(9999);
	        TThreadPoolServer.Args targs = new TThreadPoolServer.Args(tServerSocket);
	        TBinaryProtocol.Factory factory = new TBinaryProtocol.Factory();
	        //ªÒ»°processFactory
	        TProcessorFactory tProcessorFactory = new ProcessorFactoryImpl(null);
	        targs.protocolFactory(factory);
	        targs.processorFactory(tProcessorFactory);
	        TThreadPoolServer tThreadPoolServer = new TThreadPoolServer(targs); 
	        System.out.println("start server...");
	        tThreadPoolServer.serve();
	        
		} catch (TTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
}