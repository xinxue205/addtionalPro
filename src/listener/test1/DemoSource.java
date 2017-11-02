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

	   //ע����������������û��ʹ��Vector����ʹ��ArrayList��ôҪע��ͬ������
	   public void addDemoListener(DemoListener dl)
	   {
		   listener.addElement(dl);//�ⲽҪע��ͬ������
	   }

	   //ɾ�����������������û��ʹ��Vector����ʹ��ArrayList��ôҪע��ͬ������
	   public void removeDemoListener(DemoListener dl)
	   {
		   listener.remove(dl);//�ⲽҪע��ͬ������
	   }

	   //�������û��ʹ��Vector����ʹ��ArrayList��ôҪע��ͬ������
	   public void demoOpenEvent(DemoSource ds) {
		   System.out.println("source open event");
	     Enumeration listeners = listener.elements();//�ⲽҪע��ͬ������
	     synchronized (listener) {
	    	 while(listeners.hasMoreElements())
	    	 {
	    		 ListenerAdapter dl = (ListenerAdapter)listeners.nextElement();
	    		 dl.demoOpenEvent(ds);
	    	 }
		}
	   }
	   
	 //�������û��ʹ��Vector����ʹ��ArrayList��ôҪע��ͬ������
	   public void demoCloseEvent(DemoSource ds) {
		   System.out.println("source close event");
	     Enumeration listeners = listener.elements();//�ⲽҪע��ͬ������
	     synchronized (listener) {
	    	 while(listeners.hasMoreElements())
	    	 {
	    		 ListenerAdapter dl = (ListenerAdapter)listeners.nextElement();
	    		 dl.demoCloseEvent(ds);
	    	 }
		}
	   }
}