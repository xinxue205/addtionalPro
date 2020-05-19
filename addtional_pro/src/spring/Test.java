package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) throws ClassNotFoundException {
		//System.out.println(Class.forName("spring.LowerToUpper").getMethods()[0]);
		ApplicationContext changeLetter = new ClassPathXmlApplicationContext("spring/beans.xml");
		
		UpperToLower utl = (UpperToLower) changeLetter.getBean("upperToLower");
		utl.setStr("ABB");
		System.out.println(utl.change());
		
		LowerToUpper ltu = (LowerToUpper) changeLetter.getBean("lowerToUpper");
		ltu.setStr("def");
		System.out.println(ltu.change());
		
	}
}
