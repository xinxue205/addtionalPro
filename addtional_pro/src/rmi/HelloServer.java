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
 * @time 2015-6-19 ����3:45:54
 * @copyright hnisi
 */
public class HelloServer { 
	
	public static String ip = "localhost";
	public static int port = 6500;
	public static String serviceName = "RHello";
	
	//�󶨵�URL��׼��ʽΪ��rmi://host:port/name(����Э��������ʡ�ԣ���������д��������ȷ�ģ� 
	static String serviceUrl = "rmi://"+ ip +":"+ port +"/" + serviceName;
//  static String serviceUrl = "//localhost:8888/RHello"; 

	
    public static void main(String args[]) { 

        try { 
            //����һ��Զ�̶��� 
            IHello rhello = new HelloImpl(); 
            //���������ϵ�Զ�̶���ע���Registry��ʵ������ָ���˿ڣ���һ���ز����٣�JavaĬ�϶˿���1099�����ز���ȱ��һ����ȱ��ע����������޷��󶨶���Զ��ע����� 
            Registry r = LocateRegistry.createRegistry(port);

            //��Զ�̶���ע�ᵽRMIע���������
            Naming.bind(serviceUrl, rhello); //����1  ��url
//            r.bind(serviceName, rhello);//����2  �÷�����

            System.out.println(">>>>>INFO:Զ��IHello����󶨳ɹ���"); 
        } catch (Exception e) { 
            System.out.println("�����ظ��󶨶����쳣��"); 
            e.printStackTrace(); 
        } 
    } 
}
