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
		LOG.info("�յ�����ˣ�"+str);
	}
	
	public void process(){
		processor = new ServerCallbackService.Processor<ServerCallbackService.Iface>(this);
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
	
	protected int nMsgCount = 0;
	protected TSocket socket;
	protected TProcessor processor;
}
