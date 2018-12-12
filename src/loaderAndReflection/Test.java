package loaderAndReflection;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;


public class Test {
	public static void main1(String[] args) throws ClassNotFoundException {
		System.out.println(Class.forName("loaderAndReflection.ClassLoaderAttachment").getName());;
		ClassLoaderAttachment cla = new ClassLoaderAttachment();
		System.out.println(cla.toString());;
		System.out.println(Class.forName("loaderAndReflection.CommonLoader").getName());;
		CommonLoader cl = new CommonLoader();
		cl.sayHello();
	}
	
	public static void main(String[] args) throws Exception {
		URL[] urls = new URL[1];
		urls[0] = new File("D:/download/classes/").toURL();
		ClassLoader cl = new URLClassLoader(urls); 
		Class cls = cl.loadClass("cn.sinobest.knob.ganger.persist.model.SchedJob"); 
		Field[] fs = cls.getDeclaredFields();
		for(int i = 0 ; i < fs.length; i++){  
	           Field f = fs[i];  
	           f.setAccessible(true); //设置些属性是可以访问的  
	           Object val = f.get(cls);//得到此属性的值     
	        
	           System.out.println("name:"+f.getName()+"\t value = "+val);  
	            
	           String type = f.getType().toString();//得到此属性的类型  
	           if (type.endsWith("String")) {  
	              System.out.println(f.getType()+"\t是String");  
	           }else if(type.endsWith("int") || type.endsWith("Integer")){  
	              System.out.println(f.getType()+"\t是int");  
	           }else{  
	              System.out.println(f.getType()+"\t");  
	           }  
	            
	       }
	}
}
