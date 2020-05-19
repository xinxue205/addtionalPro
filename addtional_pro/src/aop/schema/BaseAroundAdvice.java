package aop.schema;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * ����֪ͨ
 * 
 * @author yanbin
 * 
 */
public class BaseAroundAdvice implements MethodInterceptor {

    /**
     * invocation �����ӵ�
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("===========����around���Ʒ�����=========== \n");

        // ����Ŀ�귽��֮ǰִ�еĶ���
        System.out.println("���÷���֮ǰ: ִ�У�\n");

        // ���÷����Ĳ���
        Object[] args = invocation.getArguments();
        // ���õķ���
        Method method = invocation.getMethod();
        // ��ȡĿ�����
        Object target = invocation.getThis();
        // ִ���귽���ķ���ֵ������proceed()�������ͻᴥ������㷽��ִ��
        Object returnValue = invocation.proceed();

        System.out.println("===========��������around���Ʒ�����=========== \n");

        System.out.println("�����" + args[0] + ";" + method + ";" + target + ";" + returnValue + "\n");

        System.out.println("���÷���������֮��ִ�У�\n");

        return returnValue;
    }

}
