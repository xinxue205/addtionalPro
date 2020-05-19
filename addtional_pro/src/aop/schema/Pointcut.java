package aop.schema;

import java.lang.reflect.Method;

import org.springframework.aop.support.NameMatchMethodPointcut;

/**
 * ����һ���е㣬ָ����Ӧ����ƥ�䡣������������Է������д���<br>
 * 
 * �̳�NameMatchMethodPointcut�࣬���÷�����ƥ��
 * 
 * @author yanbin
 * 
 */
public class Pointcut extends NameMatchMethodPointcut {

    private static final long serialVersionUID = 3990456017285944475L;

    @SuppressWarnings("rawtypes")
    @Override
    public boolean matches(Method method, Class targetClass) {
        // ���õ�������ƥ��
        this.setMappedName("delete");
        // ���ö������ƥ��
        String[] methods = { "delete", "modify" };

        //Ҳ�����á� * �� ����ƥ�����
        // this.setMappedName("get*");

        this.setMappedNames(methods);

        return super.matches(method, targetClass);
    }

}
