package singleton;

/**
 * 单例模式（公开的获取实例的getInstance方法取得的是该类的的静态对象）
 * 
 * @author wxx
 */
public class SingletonTest {
	private SingletonTest(){}
	private static SingletonTest instance = new SingletonTest();
	public static SingletonTest getInstance(){
		return instance;
	}
}
