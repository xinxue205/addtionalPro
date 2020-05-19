package gc;

import static java.lang.management.ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE;
import static java.lang.management.ManagementFactory.newPlatformMXBeanProxy;

import java.io.File;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.sun.tools.attach.VirtualMachine;

public class GCTest3 {
	static SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	
	public static void main(String[] args) throws Throwable{
//		List<GarbageCollectorMXBean> gcmbeans = getGarbageCollectorMXBeans();
		String pid = "13044";
		
		VirtualMachine virtualmachine = VirtualMachine.attach(pid);
		String javaHome = virtualmachine.getSystemProperties().getProperty("java.home");
		String jmxAgent = javaHome + File.separator + "lib" + File.separator + "management-agent.jar";
		virtualmachine.loadAgent(jmxAgent, "com.sun.management.jmxremote");

		//������ӵ�ַ
		Properties properties = virtualmachine.getAgentProperties();
		String address = (String)properties.get("com.sun.management.jmxremote.localConnectorAddress");

		//Detach
//		virtualmachine.detach();
		JMXServiceURL url = new JMXServiceURL(address);
		JMXConnector jmxc = JMXConnectorFactory.connect(url);
		MBeanServerConnection server = jmxc.getMBeanServerConnection();
		ObjectName gcName = null;
		try {
			gcName = new ObjectName(GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE + ",*");
		} catch (MalformedObjectNameException e) {
			// should not reach here
			assert (false);
		}

		Set<ObjectName> mbeans = server.queryNames(gcName, null);
		if (mbeans != null) {
			List<GarbageCollectorMXBean> gcmbeans = new ArrayList<GarbageCollectorMXBean>();
			Iterator<ObjectName> iterator = mbeans.iterator();
			while (iterator.hasNext()) {
				ObjectName objName = iterator.next();
				GarbageCollectorMXBean gc = newPlatformMXBeanProxy(server,
						objName.getCanonicalName(),
						GarbageCollectorMXBean.class);
				gcmbeans.add(gc);
			}
			gatherGC(gcmbeans);
		}
	}
	
	/** 
     * Java ������е������������� 
     */  
    public static void showGarbageCollector(){  
        List<GarbageCollectorMXBean> gc = ManagementFactory.getGarbageCollectorMXBeans();  
        for(GarbageCollectorMXBean GarbageCollectorMXBean : gc){  
            System.out.println("name:" + GarbageCollectorMXBean.getName());   
            System.out.println("CollectionCount:" + GarbageCollectorMXBean.getCollectionCount());  
            System.out.println("CollectionTime" + GarbageCollectorMXBean.getCollectionTime());    
        }  
    }
    
    /**
	 * ��ȡJVM GC��Ϣ������ǰ��GCʱ���ȥ��һ�ε�GCʱ��ɻ�õ�ǰ��GC��ʱ��
	 *
	 * @return Map:key=GC���ͣ�PS Scavenge,PS
	 *         MarkSweep�ȣ���ʾΪminorGC��FullGC����ͬ��GC���ԣ���ֵ���ܻ᲻ͬ��
	 *         value=long[],����long[0]:GC�ܴ�����long[1]:GC��ʱ��
     * @throws Throwable 
	 */
	public static void gatherGC(List<GarbageCollectorMXBean> gcmbeans) throws Throwable {
		
		while (true) {
			StringBuffer str = new StringBuffer();
			for (short i = 0; i < gcmbeans.size(); i++) {
				GarbageCollectorMXBean gcBean = gcmbeans.get(i);
				
//				System.out.format("%s %d %d\n", gcBean.getName(), gcBean.getCollectionCount(), gcBean.getCollectionTime()  );
				str.append("\t" + gcBean.getName() + "\t" + gcBean.getCollectionCount() + "\t" + gcBean.getCollectionTime() );
//			result.put(
//					gcBean.getName(),
//					new long[] { gcBean.getCollectionCount(),
//							gcBean.getCollectionTime() });
			}
			System.out.println(df.format(new Date()) + str.toString());
			Thread.sleep(2222);
		}
	}
}
