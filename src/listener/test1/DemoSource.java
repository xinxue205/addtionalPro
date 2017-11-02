package listener.test1;

import java.util.Enumeration;
import java.util.Vector;

public class DemoSource{
	   private Vector listener = new Vector();
	   String name;

	   public DemoSource(String name)
	   {
		   this.name=name;
	   }

	   //注册监听器，如果这里没有使用Vector而是使用ArrayList那么要注意同步问题
	   public void addDemoListener(DemoListener dl)
	   {
		   listener.addElement(dl);//这步要注意同步问题
	   }

	   //删除监听器，如果这里没有使用Vector而是使用ArrayList那么要注意同步问题
	   public void removeDemoListener(DemoListener dl)
	   {
		   listener.remove(dl);//这步要注意同步问题
	   }

	   //如果这里没有使用Vector而是使用ArrayList那么要注意同步问题
	   public void demoOpenEvent(DemoSource ds) {
		   System.out.println("source open event");
	     Enumeration listeners = listener.elements();//这步要注意同步问题
	     synchronized (listener) {
	    	 while(listeners.hasMoreElements())
	    	 {
	    		 ListenerAdapter dl = (ListenerAdapter)listeners.nextElement();
	    		 dl.demoOpenEvent(ds);
	    	 }
		}
	   }
	   
	 //如果这里没有使用Vector而是使用ArrayList那么要注意同步问题
	   public void demoCloseEvent(DemoSource ds) {
		   System.out.println("source close event");
	     Enumeration listeners = listener.elements();//这步要注意同步问题
	     synchronized (listener) {
	    	 while(listeners.hasMoreElements())
	    	 {
	    		 ListenerAdapter dl = (ListenerAdapter)listeners.nextElement();
	    		 dl.demoCloseEvent(ds);
	    	 }
		}
	   }
}