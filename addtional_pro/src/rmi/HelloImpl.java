/**
 * 
 */
package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author wuxinxue
 * @time 2015-6-19 ����3:45:13
 * @copyright hnisi
 */
public class HelloImpl extends UnicastRemoteObject implements IHello { 
    /**
	 * 
	 */
	private static final long serialVersionUID = -979516930381411623L;

	/** 
     * ��ΪUnicastRemoteObject�Ĺ��췽���׳���RemoteException�쳣���������Ĭ�ϵĹ��췽������д�����������׳�RemoteException�쳣 
     * 
     * @throws RemoteException 
     */ 
    public HelloImpl() throws RemoteException { 
    } 

    /** 
     * �򵥵ķ��ء�Hello World��"���� 
     * 
     * @return ���ء�Hello World��"���� 
     * @throws java.rmi.RemoteException 
     */ 
    public String helloWorld() throws RemoteException { 
    	System.out.println("tingdao!");
        return "Hello World!"; 
    } 

    /** 
     * һ���򵥵�ҵ�񷽷������ݴ��������������Ӧ���ʺ��� 
     * 
     * @param someBodyName ���� 
     * @return ������Ӧ���ʺ��� 
     * @throws java.rmi.RemoteException 
     */ 
    public String sayHelloToSomeBody(String someBodyName) throws RemoteException { 
        return "��ã�" + someBodyName + "!"; 
    } 
}
