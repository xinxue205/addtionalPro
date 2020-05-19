package spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext ac=new ClassPathXmlApplicationContext("spring/aop/beans.xml");
		TestServiceInter ts=(TestServiceInter) ac.getBean("proxyFactoryBean");
		ts.sayHello();
		System.out.println(ts.getClass().getName());
		((TestServiceInter2)ts).sayBye();
		System.out.println(ts.getClass().getName());
		//((Test1Service)ts).sayHello();
	}

}
