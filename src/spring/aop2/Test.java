/**
 * 
 */
package spring.aop2;

/**
 * ֱ��JAVA����ʵ��AOP
 * @Author&Copyright xxxx��˾
 * @version ����10:50:38 Administrator 
 */
public class Test {
	public static void main(String[] args) {
		ProxyHandler ph = new ProxyHandler();
		Service f = new Service();
		Interface1 f1 = (Interface1) ph.bind(f); 
		f1.sayHello();
		System.out.println(f1.getClass().getName());
		((Interface2)f1).sayBye();
		System.out.println(f1.getClass().getName());
	}
}
