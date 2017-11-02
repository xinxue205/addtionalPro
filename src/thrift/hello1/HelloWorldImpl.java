package thrift.hello1;

import org.apache.thrift.TException; 

/** 
 *  blog http://www.micmiu.com 
 * @author Michael 
 */
public class HelloWorldImpl implements HelloWorldService.Iface {
	public HelloWorldImpl() {
		
	}
	
	@Override	
	public String sayHello(String username, String nickName, String passWord) throws TException {	
		return "Hi," + username + " welcome to my blog www.micmiu.com";	
	} 
}
