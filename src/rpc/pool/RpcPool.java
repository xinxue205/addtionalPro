package rpc.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.ndi.worker.rpc.Process;
import org.ndi.worker.rpc.broker.WorkerBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RpcPool {
	
	List<ConnectionProxy> allConnection = new ArrayList<ConnectionProxy>();

	ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(11);
	
	boolean start(){
		//ªÒ»°º‡øÿ¡–±Ì
		ConnectionInfo ci1 = new ConnectionInfo("192.168.11.115", 1082);
		ConnectionInfo ci2 = new ConnectionInfo("localhost", 6667);
		ConnectionInfo ci3 = new ConnectionInfo("localhost", 4444);
		ConnectionInfo[] si = {ci1, ci2, ci3};
		for (ConnectionInfo i : si) {
			ConnectionProxy monitor = new ConnectionProxy(new ConnectionInfo(i.getAddr(), i.getPort()));
			scheduler.scheduleWithFixedDelay(monitor, 5, 5, TimeUnit.SECONDS);
			allConnection.add(monitor);
		}
		return true;
		//∆Ù∂Øº‡øÿ
	}
	
	
	boolean stop() {
		cleanAllConnection();
		scheduler.shutdown();
		return true;
		//Õ£÷πº‡øÿ
	}


	private void cleanAllConnection() {
		for (ConnectionProxy conn : allConnection) {
			conn.setOk(false);
			conn.setUsing(false);
			RpcUtil.closeQuiet(conn.getClient());
		}
	}
	
	public ConnectionProxy getAConnection() {
		Collections.shuffle(allConnection);
		for (ConnectionProxy conn : allConnection) {
			if(!conn.isUsing() && conn.isOk()) {
				conn.setUsing(true);
				return conn;
			}
		} 
		try {
			Thread.sleep(3*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return getAConnection();
	}
	
	public void returnAConnection(ConnectionProxy conn){
		conn.setUsing(false);
	}
}

class ConnectionProxy implements Runnable{
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionProxy.class);

	private ConnectionInfo connectionInfo;
	private WorkerBroker.Client client;
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
		if(using) {
			return;
		}
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
				}
			}
		}
	}
	
	private boolean test(){
		if(null == client) {
			return false;
		}
		//≤‚ ‘¡¨Ω”
		try {
			client.call(Process.TEST.getName(), "", "");
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

	public WorkerBroker.Client getClient() {
		return client;
	}

	public void setClient(WorkerBroker.Client client) {
		this.client = client;
	}

	/**
	 * @return the using
	 */
	public boolean isUsing() {
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
