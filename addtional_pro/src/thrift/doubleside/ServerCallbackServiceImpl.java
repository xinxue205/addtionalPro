package thrift.doubleside;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class ServerCallbackServiceImpl implements ServerCallbackService.Iface{
	private static final Logger LOG = LoggerFactory.getLogger(ServerCallbackServiceImpl.class);
	public ServerCallbackServiceImpl(TSocket socket){
		this.socket = socket;
	}
	
	@Override
	public void Push(String msg) throws TException {
		// TODO Auto-generated method stub
		String str = String.format("receive msg %d: %s", nMsgCount++, msg);
		LOG.info("收到服务端："+str);
	}
	
	public void process(){
		processor = new ServerCallbackService.Processor<ServerCallbackService.Iface>(this);
		 TBinaryProtocol protocol = new TBinaryProtocol(socket);
		 while (true)
         {
             try
             {
            	 //TProcessor，负责调用用户定义的服务接口，从一个接口读入数据，写入一个输出接口
                 while (processor.process(protocol, protocol)){
                	//阻塞式方法,不需要内容
                     System.out.println("走阻塞式方法");
                     //关闭socket
                     //socket.close();
                 }
                 //connection lost, return
                 return;
             }catch (TException e){
            	 System.out.println("连接已断开...");
                 e.printStackTrace();
                 return;
             }
         }
	}
	
	protected int nMsgCount = 0;
	protected TSocket socket;
	protected TProcessor processor;
}
