package aop.aspeckj;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SleepHelper {

    public SleepHelper(){
        
    }
    
    @Pointcut("execution(* *.sleep())")
    public void sleeppoint(){}
    
    @Before("sleeppoint()")
    public void beforeSleep(){
    	 System.out.println(  "˯��ǰҪ���·�!");
    }
    
    @AfterReturning("sleeppoint()")
    public void afterSleep(){
    	 System.out.println( "˯����Ҫ���·���");
    }
    
}