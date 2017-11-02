package loaderAndReflection;

class X{
	
}

public class Demo1 {
	public static void main(String[] args) {
		Class<?> c1 = null;
		Class<?> c2 = null;
		Class<?> c3 = null;
		try {
			c1 = Class.forName("injection.X");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c2 = new X().getClass();
		c3 = X.class;
		System.out.println("Class Name: " + c1.getName());
		System.out.println("Class Name: " + c2.getName());
		System.out.println("Class Name: " + c3.getName());
	}	
}
