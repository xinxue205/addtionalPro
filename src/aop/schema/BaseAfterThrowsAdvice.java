package aop.schema;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

/**
 * �쳣֪ͨ���ӿ�û�а����κη�����֪ͨ�����Զ���
 * 
 * @author yanbin
 * 
 */
public class BaseAfterThrowsAdvice implements ThrowsAdvice {

    /**
     * ֪ͨ��������Ҫ�������ָ�ʽ��д
     * 
     * @param method
     *            ��ѡ������ķ���
     * @param args
     *            ��ѡ������ķ����Ĳ���
     * @param target
     *            ��ѡ��Ŀ�����
     * @param throwable
     *            ���� : �쳣���࣬��������쳣������࣬���������֪ͨ��
     */
    public void afterThrowing(Method method, Object[] args, Object target, Throwable throwable) {
        System.out.println("ɾ��������");
    }

}