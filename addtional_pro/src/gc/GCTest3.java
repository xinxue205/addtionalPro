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

		//获得连接地址
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
     * Java 虚拟机中的垃圾回收器。 
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
	 * 获取JVM GC信息，将当前次GC时间减去上一次的GC时间可获得当前次GC的时间
	 *
	 * @return Map:key=GC类型（PS Scavenge,PS
	 *         MarkSweep等，表示为minorGC和FullGC，不同的GC策略，此值可能会不同）
	 *         value=long[],其中long[0]:GC总次数，long[1]:GC总时间
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
