package aop.aspeckj;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Test {

    public static void main(String[] args){
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("aop-aspeckj.xml");
        Sleepable human = (Sleepable)appCtx.getBean("human");
        human.sleep();
    }
}
