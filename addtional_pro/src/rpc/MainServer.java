package rpc;

import rpc.op.Echo;
import rpc.op.RemoteEcho;
import rpc.support.Server;



public class MainServer {
	public static int port = 25335;
	
	public static void main(String[] args) {
		Server server = new RPC.RPCServer(port);
		server.register(Echo.class, RemoteEcho.class);
		server.start();
	}

}
