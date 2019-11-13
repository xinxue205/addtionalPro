package rpc.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.sinobest.knob.core.worker.WorkerBroker;
import cn.sinobest.knob.core.worker.WorkerBroker.Client;

public class RpcPool {
	
	List<ConnectionProxy> allConnection = new ArrayList<ConnectionProxy>();

	ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(11);
	
	boolean start(){
		//获取监控列表
		ConnectionInfo ci1 = new ConnectionInfo("192.168.11.115", 1081);
		ConnectionInfo ci2 = new ConnectionInfo("192.168.11.115", 2222);
		ConnectionInfo ci3 = new ConnectionInfo("localhost", 1081);
		ConnectionInfo[] si = {ci1, ci2, ci3};
		for (int i = 0; i < si.length; i++) {
			ConnectionProxy monitor = new ConnectionProxy(new ConnectionInfo(si[i].getAddr(), si[i].getPort()));
			scheduler.scheduleWithFixedDelay(monitor, 5, 5, TimeUnit.SECONDS);
			allConnection.add(monitor);
		}
		return true;
		//启动监控
	}
	
	
	boolean stop() {
		scheduler.shutdown();
		cleanAllConnection();
		return true;
		//停止监控
	}


	private void cleanAllConnection() {
		// TODO Auto-generated method stub
	}
	
	public ConnectionProxy getAConnection() throws Exception{
		for (int i = 0; i < allConnection.size(); i++) {
			ConnectionProxy conn = allConnection.get(i);
			if(!conn.getUsing() && conn.isOk()) {
				conn.setUsing(true);
				return conn;
			}
		} 
		throw new Exception("all connection are inavailable!");
	}
	
	public void returnAConnection(ConnectionProxy conn){
		conn.setUsing(false);
	}
}

class ConnectionProxy implements Runnable{
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionProxy.class);

	private ConnectionInfo connectionInfo;
	private Client client;
	private boolean using;
	private boolean ok;
	private long checkTime;
	


	public ConnectionProxy(ConnectionInfo connectionInfo) {
		try {
			client = RpcUtil.newClient(connectionInfo.getAddr(), connectionInfo.getPort(), WorkerBroker.Client.class);
			setOk(true);
		} catch (Throwable e) {
			LOG.warn(connectionInfo.getAddr()+":"+connectionInfo.getPort()+" connect error: "+e.getMessage());
			setOk(false);
		}
		this.connectionInfo = connectionInfo;
	}

	@Override
	public void run() {
		try {
			synchronized (this) {
				setCheckTime(System.currentTimeMillis());
				boolean isOk = test();
				setOk(isOk);
				if(!ok) {
					RpcUtil.closeQuiet(client);
					try {
						LOG.debug(connectionInfo.getAddr()+":"+connectionInfo.getPort()+" try to reconnect!!!");
						client = RpcUtil.newClient(connectionInfo.getAddr(), connectionInfo.getPort(), WorkerBroker.Client.class);
						setOk(true);
					} catch (Throwable e) {
						LOG.warn(connectionInfo.getAddr()+":"+connectionInfo.getPort()+" connect error: "+e.getMessage());
						setOk(false);
					}
				}
			}
		} catch (Throwable e) {
			LOG.error(connectionInfo.getAddr()+":"+connectionInfo.getPort()+" connect error: ", e);
		}
	}
	
	private boolean test(){
		if(null == client) {
			return false;
		}
		//测试连接
		try {
			client.ping("");
			return true;
		} catch (Throwable e) {
			LOG.warn(connectionInfo.getAddr()+":"+connectionInfo.getPort()+" connect error: "+e.getMessage());
			return false;
		}
	}
	
	
	public ConnectionInfo getConnectionInfo() {
		return connectionInfo;
	}

	public void setConnectionInfo(ConnectionInfo connectionInfo) {
		this.connectionInfo = connectionInfo;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * @return the using
	 */
	public boolean getUsing() {
		return using;
	}

	/**
	 * @param using the using to set
	 */
	public void setUsing(boolean using) {
		this.using = using;
	}

	/**
	 * @return the ok
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @param ok the ok to set
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * @return the checkTime
	 */
	public long getCheckTime() {
		return checkTime;
	}

	/**
	 * @param checkTime the checkTime to set
	 */
	public void setCheckTime(long checkTime) {
		this.checkTime = checkTime;
	}
}
