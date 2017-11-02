/**
 * 
 */
package spring.aop2;

/**
 * 直接JAVA代码实现AOP
 * @Author&Copyright xxxx公司
 * @version 下午10:50:38 Administrator 
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
