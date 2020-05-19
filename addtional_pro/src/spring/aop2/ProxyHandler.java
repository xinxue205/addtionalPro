/**
 * 
 */
package spring.aop2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * @Author&Copyright xxxx公司
 * @version 下午10:50:38 Administrator 
 */
public class ProxyHandler implements InvocationHandler {
	private Object service;    //被代理对象 
    private Interceptor interceptor = new Interceptor();    //拦截器 

    /** 
     * 动态生成一个代理类对象,并绑定被代理类和代理处理器 
     * 
     * @param service 
     * @return 代理类对象 
     */ 
    public Object bind(Object service) { 
        this.service = service; 
        return java.lang.reflect.Proxy.newProxyInstance( 
                //被代理类的ClassLoader 
                service.getClass().getClassLoader(), 
                //要被代理的接口,本方法返回对象会自动声称实现了这些接口 
                service.getClass().getInterfaces(), 
                //代理处理器对象 
                this); 
    } 

    /** 
     * 代理要调用的方法,并在方法调用前后调用连接器的方法. 
     * 
     * @param proxy  代理类对象 
     * @param method 被代理的接口方法 
     * @param args   被代理接口方法的参数 
     * @return 方法调用返回的结果 
     * @throws Throwable 
     */ 
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable { 
        Object result = null; 
        interceptor.before(); 
        result=method.invoke(service,args); 
        interceptor.after(); 
        return null;  //To change body of implemented methods use File | Settings | File Templates. 
    } 
}
