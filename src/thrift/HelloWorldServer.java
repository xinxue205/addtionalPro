package thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import thrift.hello1.HelloWorldImpl;
import thrift.hello1.HelloWorldService;
import thrift.hello1.HelloWorldService.Iface;
import thrift.hello1.HelloWorldService.Processor;
import thrift.hello2.HelloWorldImpl1;
import thrift.hello2.HelloWorldService1;

/**
 * blog http://www.micmiu.com
 *
 * @author Michael
 *
 */
public class HelloWorldServer {
	public static final int SERVER_PORT = 8090;

	
	public void startServer() {
		try {
			System.out.println("HelloWorld TSimpleServer start ....");
			HelloWorldService.Processor<HelloWorldService.Iface> tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());
			 
			TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
			TServer.Args tArgs = new TServer.Args(serverTransport);
			tArgs.processor(tprocessor);
			tArgs.protocolFactory(new TBinaryProtocol.Factory());
			// tArgs.protocolFactory(new TCompactProtocol.Factory());
			// tArgs.protocolFactory(new TJSONProtocol.Factory());
			TServer server = new TSimpleServer(tArgs);
			server.serve();

		} catch (Exception e) {
			System.out.println("Server start error!!!");
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("HelloWorld TSimpleServer start ....");
		TMultiplexedProcessor processor = new TMultiplexedProcessor();
	    processor.registerProcessor("HelloServer", new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl()));
	    processor.registerProcessor("HelloServer1", new HelloWorldService1.Processor<HelloWorldService1.Iface>(new HelloWorldImpl1()));
		
		TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
		Args tArgs = new TThreadPoolServer.Args(serverTransport).maxWorkerThreads(32).minWorkerThreads(2);
	    TServer server = new TThreadPoolServer(tArgs.processor(processor));
	    server.serve();
	    
//		HelloServerDemo server = new HelloServerDemo();
//		server.startServer();
	}

}