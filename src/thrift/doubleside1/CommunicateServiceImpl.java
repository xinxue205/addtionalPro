package thrift.doubleside1;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import thrift.doubleside.ServerCallbackService;
 
public class CommunicateServiceImpl implements CommunicateService.Iface {
	
	static Map map = new HashMap();
	
	
	protected TProcessor processor;
	protected TSocket socket;
	
	public CommunicateServiceImpl(TTransport trans){
		this.socket = trans;
		map.put(trans, new Date().getTime());
	}

	@Override
	public void say(String msg) throws TException {
		System.out.println(msg);
	}
	
	@Override
	protected void finalize() throws Throwable {
		map.remove(map);
		super.finalize();
	}

	public void process() {
		processor = new CommunicateService.Processor<CommunicateService.Iface>(this);
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
	
}
