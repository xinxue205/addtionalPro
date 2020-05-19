package aop.schema;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

/**
 * 后置通知
 * 
 * @author yanbin
 * 
 */
public class BaseAfterReturnAdvice implements AfterReturningAdvice {

    /**
     * returnValue ：切入点执行完方法的返回值，但不能修改 <br>
     * method ：切入点方法 <br>
     * args ：切入点方法的参数数组 <br>
     * target ：目标对象
     */
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("==========进入afterReturning()=========== \n");
        System.out.println("切入点方法执行完了 \n");

        System.out.print(args[0] + "在");
        System.out.print(target + "对象上被");
        System.out.print(method + "方法删除了");
        System.out.print("只留下：" + returnValue + "\n\n");
    }

}
