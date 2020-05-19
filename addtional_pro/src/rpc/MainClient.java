package rpc;

import rpc.op.Echo;


public class MainClient {
	public static void main(String[] args) {
		Echo echo = RPC.getProxy(Echo.class, "192.168.59.1", MainServer.port);
		
		System.out.println("-------" + echo.echo("hello,hello"));
		System.out.println("-------" + echo.echo("hellow,rod"));
		System.out.println("-------" + echo.echo("hellow,rod"));
		System.out.println("-------" + echo.echo("hellow,rod"));
		System.out.println("-------" + echo.echo("hellow,rod"));
		System.out.println("-------" + echo.echo("hellow,rod"));
	}
}
