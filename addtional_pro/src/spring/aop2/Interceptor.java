/**
 * 
 */
package spring.aop2;

/**
 *
 * @Author&Copyright xxxx公司
 * @version 下午10:50:38 Administrator 
 */
public class Interceptor  {
	 public void before(){ 
	        System.out.println("拦截器InterceptorClass方法调用:before()!"); 
	    } 
	    public void after(){ 
	        System.out.println("拦截器InterceptorClass方法调用:after()!"); 
	    } 

}
