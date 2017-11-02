/**
 * 
 */
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author wuxinxue
 * @time 2015-6-19 ����3:44:20
 * @copyright hnisi
 */
public interface IHello extends Remote { 

    /** 
     * �򵥵ķ��ء�Hello World��"���� 
     * @return ���ء�Hello World��"���� 
     * @throws java.rmi.RemoteException 
     */ 
    public String helloWorld() throws RemoteException; 

    /** 
     * һ���򵥵�ҵ�񷽷������ݴ��������������Ӧ���ʺ��� 
     * @param someBodyName  ���� 
     * @return ������Ӧ���ʺ��� 
     * @throws java.rmi.RemoteException 
     */ 
    public String sayHelloToSomeBody(String someBodyName) throws RemoteException; 
}