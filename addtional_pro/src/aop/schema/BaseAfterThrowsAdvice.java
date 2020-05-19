package aop.schema;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

/**
 * 异常通知，接口没有包含任何方法。通知方法自定义
 * 
 * @author yanbin
 * 
 */
public class BaseAfterThrowsAdvice implements ThrowsAdvice {

    /**
     * 通知方法，需要按照这种格式书写
     * 
     * @param method
     *            可选：切入的方法
     * @param args
     *            可选：切入的方法的参数
     * @param target
     *            可选：目标对象
     * @param throwable
     *            必填 : 异常子类，出现这个异常类的子类，则会进入这个通知。
     */
    public void afterThrowing(Method method, Object[] args, Object target, Throwable throwable) {
        System.out.println("删除出错啦");
    }

}