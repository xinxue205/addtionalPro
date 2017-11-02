package aop.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import aop.aspeckj.Sleepable;

public class Test {
	
	public static void main(String[] args) {
		defaultInterceptor();
	}

    public static void commonInterceptor(){
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("aop-common.xml");
        Sleepable sleeper = (Sleepable)appCtx.getBean("humanProxy");
        sleeper.sleep();
    }
    
    public static void defaultInterceptor(){
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
        Sleepable sleeper = (Sleepable)appCtx.getBean("human");
        sleeper.sleep();
    }
}