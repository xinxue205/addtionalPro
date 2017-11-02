package singleton;

/**
 * ����ģʽ�������Ļ�ȡʵ����getInstance����ȡ�õ��Ǹ���ĵľ�̬����
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
