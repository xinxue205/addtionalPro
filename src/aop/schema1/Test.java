package aop.schema1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("aop-schema1.xml");
        AspectBusiness business = (AspectBusiness) context.getBean("aspectBusiness");
        business.delete("è");
    }

}