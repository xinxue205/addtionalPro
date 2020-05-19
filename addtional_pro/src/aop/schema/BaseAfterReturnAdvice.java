package aop.schema;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

/**
 * ����֪ͨ
 * 
 * @author yanbin
 * 
 */
public class BaseAfterReturnAdvice implements AfterReturningAdvice {

    /**
     * returnValue �������ִ���귽���ķ���ֵ���������޸� <br>
     * method ������㷽�� <br>
     * args ������㷽���Ĳ������� <br>
     * target ��Ŀ�����
     */
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("==========����afterReturning()=========== \n");
        System.out.println("����㷽��ִ������ \n");

        System.out.print(args[0] + "��");
        System.out.print(target + "�����ϱ�");
        System.out.print(method + "����ɾ����");
        System.out.print("ֻ���£�" + returnValue + "\n\n");
    }

}
