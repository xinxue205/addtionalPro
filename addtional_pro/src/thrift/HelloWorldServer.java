package thrift;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import org.apache.thrift.TException;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransport;
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
	
	Map map = new HashMap();

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
		
//		TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
//        TThreadPoolServer.Args targs=new TThreadPoolServer.Args(serverTransport);
//         //获取processFactory
//         TProcessorFactory tProcessorFactory= getProcessorFactory();
//         targs.protocolFactory(new TBinaryProtocol.Factory());
//         targs.processorFactory(tProcessorFactory);
//         TThreadPoolServer tThreadPoolServer=new TThreadPoolServer(targs);
//         System.out.println("start server...");
//         new Timer().schedule(
//        		 new java.util.TimerTask() { public void run() {
//        			 
//        		 	}
//        		 }, 5555, 5555);
//         tThreadPoolServer.serve();
		
		
	    
//		HelloServerDemo server = new HelloServerDemo();
//		server.startServer();
	}
	
	/**
     * 内部类获取 getProcessorFactory
     * @return
     */
    public static int tt= 0;
    public static TProcessorFactory getProcessorFactory(){
        
        TProcessorFactory tProcessorFactory=new TProcessorFactory(null){
            public TProcessor getProcessor(final TTransport tTransport){
                Thread thread = new Thread(new Runnable() {
                    
                    @Override
                    public void run() {
                        try {
                            
                            System.out.println("服务端休眠5秒后，执行响应......");
                            //延时五秒回复(延迟执行给客户端发送消息)
                            Thread.sleep(5000);
                            tt +=100;
                            System.out.println("延时五秒回复时，tt = " +tt);
                             //这里可以把client提取作为成员变量来多次使用
//                            Zthrift.Client client = new Zthrift.Client(new TBinaryProtocol(tTransport));
                            TBinaryProtocol protocol = new TBinaryProtocol(tTransport);
                            TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,"HelloServer");
                            HelloWorldService1.Client client2= new HelloWorldService1.Client(mp1);
                            System.out.println(client2.sayHello("222"));
                            
                            //给客户端响应消息
//                            client.send(new Student("....test"));
                            
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (TException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                
                return new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());
                
            }
        };
        return tProcessorFactory;
    }

}