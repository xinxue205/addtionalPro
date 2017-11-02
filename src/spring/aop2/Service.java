/**
 * 
 */
package spring.aop2;

/**
 *
 * @Author&Copyright xxxx¹«Ë¾
 * @version ÏÂÎç10:50:38 Administrator 
 */
public class Service implements Interface1, Interface2{
	String name = "service";

	/* (non-Javadoc)
	 * @see spring.aop2.Interface2#sayBye()
	 */
	public void sayBye() {
		// TODO Auto-generated method stub
		System.out.println("bye");
	}

	/* (non-Javadoc)
	 * @see spring.aop2.Interface1#sayHello()
	 */
	public void sayHello() {
		// TODO Auto-generated method stub
		System.out.println("hello");
	}

}
