/**
 * 
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author wuxinxue
 * @time 2015-6-19 下午3:45:54
 * @copyright hnisi
 */
public class HelloServer { 
	
	public static String ip = "localhost";
	public static int port = 6500;
	public static String serviceName = "RHello";
	
	//绑定的URL标准格式为：rmi://host:port/name(其中协议名可以省略，下面两种写法都是正确的） 
	static String serviceUrl = "rmi://"+ ip +":"+ port +"/" + serviceName;
//  static String serviceUrl = "//localhost:8888/RHello"; 

	
    public static void main(String args[]) { 

        try { 
            //创建一个远程对象 
            IHello rhello = new HelloImpl(); 
            //本地主机上的远程对象注册表Registry的实例，并指定端口，这一步必不可少（Java默认端口是1099），必不可缺的一步，缺少注册表创建，则无法绑定对象到远程注册表上 
            Registry r = LocateRegistry.createRegistry(port);

            //把远程对象注册到RMI注册服务器上
            Naming.bind(serviceUrl, rhello); //方法1  用url
//            r.bind(serviceName, rhello);//方法2  用服务名

            System.out.println(">>>>>INFO:远程IHello对象绑定成功！"); 
        } catch (Exception e) { 
            System.out.println("发生重复绑定对象异常！"); 
            e.printStackTrace(); 
        } 
    } 
}
