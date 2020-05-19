/**
 * 
 */
package spring.aop2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * @Author&Copyright xxxx��˾
 * @version ����10:50:38 Administrator 
 */
public class ProxyHandler implements InvocationHandler {
	private Object service;    //��������� 
    private Interceptor interceptor = new Interceptor();    //������ 

    /** 
     * ��̬����һ�����������,���󶨱�������ʹ������� 
     * 
     * @param service 
     * @return ��������� 
     */ 
    public Object bind(Object service) { 
        this.service = service; 
        return java.lang.reflect.Proxy.newProxyInstance( 
                //���������ClassLoader 
                service.getClass().getClassLoader(), 
                //Ҫ������Ľӿ�,���������ض�����Զ�����ʵ������Щ�ӿ� 
                service.getClass().getInterfaces(), 
                //������������ 
                this); 
    } 

    /** 
     * ����Ҫ���õķ���,���ڷ�������ǰ������������ķ���. 
     * 
     * @param proxy  ��������� 
     * @param method ������Ľӿڷ��� 
     * @param args   ������ӿڷ����Ĳ��� 
     * @return �������÷��صĽ�� 
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
