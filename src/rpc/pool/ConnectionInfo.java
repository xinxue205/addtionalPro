package rpc.pool;

public class ConnectionInfo {
	String addr;
	int port;
	
	
	public ConnectionInfo(String addr, int port) {
		this.addr = addr;
		this.port = port;
	}
	
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
