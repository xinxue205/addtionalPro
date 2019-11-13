package rpc.pool;

import java.util.concurrent.TimeUnit;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public abstract class RpcUtil {

	public static <T> T newClient(String rpcAddress, int rpcPort, Class<T> clazz) throws Exception{
		return newClient(rpcAddress, rpcPort, 20, clazz);
	}
	
	public static <T> T newClient(String rpcAddress, int rpcPort, int timeout_s, Class<T> clazz) throws Exception {
		TTransport transport = new TSocket(rpcAddress, rpcPort, (int)TimeUnit.SECONDS.toMillis(timeout_s));
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			return clazz.getConstructor(TProtocol.class).newInstance(protocol);
			
	}
	
	public static void closeQuiet(TServiceClient client){
		try{client.getInputProtocol().getTransport().close();}catch(Exception e){}
	}
	
	
}
