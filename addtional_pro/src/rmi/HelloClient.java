package rmi;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

public class HelloClient {
	
	public static void main(String[] args) throws Exception  {
		
		IHello r=(IHello)LocateRegistry.getRegistry(HelloServer.ip, HelloServer.port).lookup(HelloServer.serviceName);//方法1
//		IHello r = (IHello) Naming.lookup(HelloServer.serviceUrl);//方法2
		
		
		System.out.println(r.sayHelloToSomeBody("aaa"));
	}
	
}
