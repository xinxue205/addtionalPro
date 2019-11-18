package rpc.pool;

import org.apache.thrift.TException;
import org.ndi.worker.rpc.Process;

public class Test {
	public static void main(String[] args) throws Exception {
		RpcPool rp = new RpcPool();
		rp.start();
		Thread.sleep(11*1000);
		
		new Thread() {
				public void run(){
					ConnectionProxy aCon = rp.getAConnection();
					try {
						System.out.println(aCon.getClient().call(Process.TEST.getName(), "", "1"));
					} catch (TException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
					rp.returnAConnection(aCon);
					aCon = rp.getAConnection();
				}
		}.start();
		new Thread() {
			public void run(){
				ConnectionProxy aCon = rp.getAConnection();
				try {
					System.out.println(aCon.getClient().call(Process.TEST.getName(), "", "1"));
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
				rp.returnAConnection(aCon);
				aCon = rp.getAConnection();
			}
		}.start();
		
		Thread.sleep(11*1000);
		rp.stop();
	}
}
