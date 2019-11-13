package rpc.pool;

public class Test {
	public static void main(String[] args) throws Exception {
		RpcPool rp = new RpcPool();
		rp.start();
		ConnectionProxy aCon = rp.getAConnection();
		Thread.sleep(22*1000);
		aCon.getClient().ping("123");
	}
}
