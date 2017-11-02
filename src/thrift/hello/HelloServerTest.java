package thrift.hello;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import thrift.hello.HelloWorldService.Iface;
import thrift.hello.HelloWorldService.Processor.sayHello;

public class HelloServerTest {
	
	public static void main(String[] args) {
		try {    
            DCacheService.Processor processor = new DCacheService.Processor((Iface) new DCacheServiceImpl());     
 
           TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(1121);   
           TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(   
                   tnbSocketTransport);   
           tnbArgs.processor((TProcessor) processor);   
           tnbArgs.transportFactory(new TFramedTransport.Factory());   
           tnbArgs.protocolFactory(new TCompactProtocol.Factory());   
  
            // 使用非阻塞式IO，服务端和客户端需要指定TFramedTransport数据传输的方式   
            TServer server = new TNonblockingServer(tnbArgs);   
            server.serve();   
        } catch (TTransportException e) {     
            e.printStackTrace();     
        } catch (Exception e) {     
            e.printStackTrace();     
        }
	}
	
	private void client() throws TTransportException {
		String serverIP = null;
		int serverPort = 0;
		// TODO Auto-generated method stub
		TTransport transport = new TFramedTransport(new TSocket(serverIP, serverPort, 30000));   
        // 高效率的、密集的二进制编码格式进行数据传输    
         TProtocol protocol = new TCompactProtocol(transport);   
         transport.open();    
//         DCacheService.Client client = new DCacheService.Client(protocol);
	}
}

class DCacheService{

	public static class Processor <I extends Iface> extends org.apache.thrift.TBaseProcessor<I> implements org.apache.thrift.TProcessor{

		public Processor(I iface) {
		      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
		    }
		
		 private static <I extends Iface> Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> getProcessMap(Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
		      processMap.put("sayHello", new sayHello());
		      return processMap;
		    }

	}
	
	
	
}

class DCacheServiceImpl extends DCacheService{
	
}
