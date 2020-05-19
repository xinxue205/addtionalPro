package loaderAndReflection;

import java.io.IOException;
import java.util.Date;

public class ClassLoaderTest {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
		//System.out.println(new ClassLoaderAttachment().toString());;
		
		Class<?> clazz = new MyClassLoader("wxxlib").loadClass("loaderAndReflection.ClassLoaderAttachment");
		Date d1 = (Date)clazz.newInstance();
		System.out.println(d1);
		
		/*Class<?> clazz = new MyClassLoader("wxxlib").loadClass("ClassLoaderAttachment");
		Date d1 = (Date)clazz.newInstance();
		System.out.println(d1);*/
		
	}
}
