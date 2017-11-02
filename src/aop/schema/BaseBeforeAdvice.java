package aop.schema;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

/**
 * ǰ��֪ͨ��
 * 
 * @author yanbin
 * 
 */
public class BaseBeforeAdvice implements MethodBeforeAdvice {

    /**
     * method : ����ķ��� <br>
     * args �����뷽���Ĳ��� <br>
     * target ��Ŀ�����
     */
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("===========����beforeAdvice()============ \n");

        System.out.print("׼����" + target + "��������");
        System.out.print(method + "�������ж� '");
        System.out.print(args[0] + "'����ɾ����\n\n");

        System.out.println("Ҫ��������㷽���� \n");
    }

}
