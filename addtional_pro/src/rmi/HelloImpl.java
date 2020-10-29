/**
 * 
 */
package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author wuxinxue
 * @time 2015-6-19 下午3:45:13
 * @copyright hnisi
 */
public class HelloImpl extends UnicastRemoteObject implements IHello { 
    /**
	 * 
	 */
	private static final long serialVersionUID = -979516930381411623L;

	/** 
     * 因为UnicastRemoteObject的构造方法抛出了RemoteException异常，因此这里默认的构造方法必须写，必须声明抛出RemoteException异常 
     * 
     * @throws RemoteException 
     */ 
    public HelloImpl() throws RemoteException { 
    } 

    /** 
     * 简单的返回“Hello World！"字样 
     * 
     * @return 返回“Hello World！"字样 
     * @throws java.rmi.RemoteException 
     */ 
    public String helloWorld() throws RemoteException { 
    	System.out.println("收到helloWorld!");
        return "Hello World!"; 
    } 

    /** 
     * 一个简单的业务方法，根据传入的人名返回相应的问候语 
     * 
     * @param someBodyName 人名 
     * @return 返回相应的问候语 
     * @throws java.rmi.RemoteException 
     */ 
    public String sayHelloToSomeBody(String someBodyName) throws RemoteException { 
    	System.out.println("收到给"+ someBodyName +"的hello!");
        return "你好，" + someBodyName + "!"; 
    } 
}
