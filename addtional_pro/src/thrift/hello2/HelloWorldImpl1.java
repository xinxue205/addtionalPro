package thrift.hello2;

import org.apache.thrift.TException; 

/** 
 *  blog http://www.micmiu.com 
 * @author Michael 
 */
public class HelloWorldImpl1 implements HelloWorldService1.Iface {
	public HelloWorldImpl1() {
		
	}
	
	@Override	
	public String sayHello(String username) throws TException {	
		return "1Hi," + username + " welcome to my blog www.micmiu.com";	
	} 
}
