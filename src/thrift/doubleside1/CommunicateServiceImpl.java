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
           	 //TProcessor����������û�����ķ���ӿڣ���һ���ӿڶ������ݣ�д��һ������ӿ�
                while (processor.process(protocol, protocol)){
               	//����ʽ����,����Ҫ����
                    System.out.println("������ʽ����");
                    //�ر�socket
                    //socket.close();
                }
                //connection lost, return
                return;
            }catch (TException e){
           	 System.out.println("�����ѶϿ�...");
                e.printStackTrace();
                return;
            }
        }
	}
	
}
