/**
 * 
 */
package dynamic.proxy;

import java.lang.reflect.InvocationHandler;   
import java.lang.reflect.Method;   
import java.lang.reflect.Proxy;   
  
/**  
 * 动态代理类使用到了一个接口InvocationHandler和一个代理类Proxy ，这两个类配合使用实现了动态代理的功能。  
 * 那么什么是动态代理呢？  
 * 我们平常说的代理类是指： 给每个具体类写一个代理类，以后要使用某个具体类时，只要创建它的代理类的对象，然后调用代理类的方法就可以了。  
 * 可是如果现在有许多的具体类，那就需要有许多的代理类才可以，这样很显然不合适。所以动态代理就应运而生了，我们只要写一个类实现  
 * InvocationHandler 并实现它的invoke方法，然后再用Proxy的工厂方法newProxyInstance（）创建一个代理对象，这个对象同样可以实现对具体类的代理功能。  
 * 而且想代理哪个具体类，只要给Handler（以下代码中的Invoker）的构造器传入这个具体对象的实例就可以了。感觉是不是自己为该具体类造了一个代理类呢？呵呵~  
 */  

//接口类   
interface AbstractClass {   
  
    public void show();   
  
}   
  
// 具体类A   
class ClassA implements AbstractClass {

	/* (non-Javadoc)
	 * @see dynamic.proxy.AbstractClass#show()
	 */
	public void show() {
		// TODO Auto-generated method stub
		System.out.println("我是A类！");   
	}   
}   
  
// 具体类B   
class ClassB implements AbstractClass {   
  
	/* (non-Javadoc)
	 * @see dynamic.proxy.AbstractClass#show()
	 */  
    public void show() {   
        // TODO Auto-generated method stub   
        System.out.println("我是B类！");   
    }   
}   
//动态代理类，实现InvocationHandler接口   
class Invoker implements InvocationHandler {   
    AbstractClass ac;   
  
    public Invoker(AbstractClass ac) {   
        this.ac = ac;   
    }   
  
    /* (non-Javadoc)
	 * @see dynamic.proxy.AbstractClass#show()
	 */ 
    public Object invoke(Object proxy, Method method, Object[] arg) throws Throwable {   
//        //调用之前可以做一些处理   
    	System.out.println("开始代理");   
    	method.invoke(ac, arg);   
        //调用之后也可以做一些处理   
        return null;   
    }   
}   
  
/**  
 * 测试类  
 * @author 小路  
 */  
public class Test {   
  
    public static void main(String[] args) {   
        //创建具体类ClassB的处理对象   
        Invoker invoker1=new Invoker(new ClassA());   
        //获得具体类ClassA的代理   
        AbstractClass ac1 = (AbstractClass) Proxy.newProxyInstance(AbstractClass.class.getClassLoader(), new Class[] { AbstractClass.class }, invoker1);   
        //调用ClassA的show方法。   
        ac1.show();   
           
           
        //创建具体类ClassB的处理对象   
        Invoker invoker2=new Invoker(new ClassB());   
        //获得具体类ClassB的代理   
        AbstractClass ac2 = (AbstractClass) Proxy.newProxyInstance(AbstractClass.class.getClassLoader(),   
                new Class[] { AbstractClass.class }, invoker2);   
        //调用ClassB的show方法。   
        ac2.show();   
  
    }   
}
