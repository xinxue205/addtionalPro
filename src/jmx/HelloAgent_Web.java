package jmx;

import java.lang.management.ManagementFactory;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;

public class HelloAgent_Web
{
    public static void main(String[] args) throws JMException, Exception
    {
         MBeanServer server = ManagementFactory.getPlatformMBeanServer();
         ObjectName helloName = new ObjectName("jmxBean:name=hello");
         //create mbean and register mbean
         server.registerMBean(new Hello(), helloName);
         
         ObjectName adapterName = new ObjectName("HelloAgent:name=htmladapter,port=8082");   
         HtmlAdaptorServer adapter = new HtmlAdaptorServer();   
         server.registerMBean(adapter, adapterName);  
         adapter.start();
//         Thread.sleep(60*60*1000);
    }
}