package thrift.hello1;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport; 

/** 
 *  blog http://www.micmiu.com 
 * @author Michael 
 */
public class HelloWorldImpl implements HelloWorldService.Iface {
	public HelloWorldImpl() {
//		client = new HelloWorldImpl(trans);
		
	}
	
	@Override	
	public String sayHello(String username, String nickName, String passWord) throws TException {	
		return "Hi," + username + " welcome to my blog www.micmiu.com";	
	} 
}
