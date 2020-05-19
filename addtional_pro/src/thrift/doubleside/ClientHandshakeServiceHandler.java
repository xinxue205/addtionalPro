package thrift.doubleside;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TTransport;
 
public class ClientHandshakeServiceHandler implements ClientHandshakeService.Iface {
	public ClientHandshakeServiceHandler(TTransport trans){
		client = new ServerCallbackService.Client(new TBinaryProtocol(trans));
	}
	
	@Override
	public void HandShake() throws TException {
		 System.out.println("HandShake\n");
		 StartThread();
	}
 
	//开始线程
	public void StartThread(){
		if(threadCallback == null){
			stopThread = false;
			threadCallback = new Thread(new CallbackThread());
			threadCallback.start();
		}
	}
	
	//停止线程
	public void StopThread(){
		stopThread = true;
		if(threadCallback != null){
			try {
				threadCallback.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			threadCallback = null;
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		StopThread();
		super.finalize();
	}
 
 
 
	protected ServerCallbackService.Client client;
	protected boolean stopThread = false;
	protected Thread  threadCallback = null;
	
	class CallbackThread implements Runnable {
		public void run() {
			while(true){
				if(stopThread){
					break;
				}
				try {
					client.Push("aaaaaaa");
					Thread.sleep(3000);
				} catch (TException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}	
			}
		}
	};
}
