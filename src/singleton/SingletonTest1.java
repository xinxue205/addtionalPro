package singleton;

public class SingletonTest1 {
	private static SingletonTest1 instance = null;
	public static synchronized SingletonTest1 getInstance() {
		if (instance==null) instance=new SingletonTest1();
		return instance;
	}
}
