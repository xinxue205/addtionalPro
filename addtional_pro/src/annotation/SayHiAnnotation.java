/**
 * 
 */
package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * �Զ���ע�⣬�������÷���
 * @author wuxinxue
 * @time 2015-7-23 ����5:31:44
 * @copyright sinobest
 */
@Retention(RetentionPolicy.RUNTIME) // ��ʾע��������ʱ��Ȼ����
@Target(ElementType.METHOD) // ��ʾע����Ա�ʹ���ڷ�����
public @interface SayHiAnnotation {
    String paramValue() default "johness"; // ��ʾ�ҵ�ע����Ҫһ������ ��Ϊ"paramValue" Ĭ��ֵΪ"johness"
}
