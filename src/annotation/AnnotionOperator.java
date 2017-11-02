/**
 * 
 */
package annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wuxinxue
 * @time 2015-7-23 ����5:34:11
 * @copyright sinobest
 */
public class AnnotionOperator {
    public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        SayHiElement element = new SayHiElement(); // ��ʼ��һ��ʵ�������ڷ�������
        element.getClass().getAnnotations();
        Method[] methods = SayHiElement.class.getDeclaredMethods(); // ������з���
        
        for (Method method : methods) {
            SayHiAnnotation annotationTmp = null;
            if((annotationTmp = method.getAnnotation(SayHiAnnotation.class))!=null) // ����Ƿ�ʹ�������ǵ�ע��
                method.invoke(element,annotationTmp.paramValue()); // ���ʹ�������ǵ�ע�⣬���ǾͰ�ע�����"paramValue"����ֵ��Ϊ�������������÷���
            else
                method.invoke(element, "Rose"); // ���û��ʹ�����ǵ�ע�⣬���Ǿ���Ҫʹ����ͨ�ķ�ʽ�����÷�����
        }
    }
}
