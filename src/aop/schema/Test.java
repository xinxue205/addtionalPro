package aop.schema;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("aop-schema.xml");
        IBaseBusiness business = (IBaseBusiness ) context.getBean("businessProxy");
        business.delete("è");
    }

}