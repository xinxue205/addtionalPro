package thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import thrift.hello1.HelloWorldService;
import thrift.hello2.HelloWorldService1;

public class HelloWorldClient {
	public static void main(String[] args) throws Exception {
		TTransport transport = new TSocket("127.0.0.1", 8090);
		
	    transport.open();
	    TBinaryProtocol protocol = new TBinaryProtocol(transport);

        TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,"HelloServer");
        HelloWorldService.Client client1= new HelloWorldService.Client(mp1);
        System.out.println(client1.sayHello("111", "aaa", "123"));
        
        TMultiplexedProtocol mp2 = new TMultiplexedProtocol(protocol,"HelloServer1");
        HelloWorldService1.Client client2= new HelloWorldService1.Client(mp2);
        System.out.println(client2.sayHello("222"));
        
        transport.close();
	}
}
