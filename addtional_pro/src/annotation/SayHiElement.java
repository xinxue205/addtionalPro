/**
 * 
 */
package annotation;

/**
 * Ҫʹ��SayHiAnnotation��Ԫ��������
 * �������Ƕ�����ֻ�з�������ʹ�����ǵ�ע�⣬���Ǿ�ʹ�ö�����������в���
 * @author wuxinxue
 * @time 2015-7-23 ����5:33:11
 * @copyright sinobest
 */
public class SayHiElement {

    // ��ͨ�ķ���
    public void SayHiDefault(String name){
        System.out.println("Hi, " + name);
    }
    
    // ʹ��ע�Ⲣ��������ķ���
    @SayHiAnnotation(paramValue="Jack")
    public void SayHiAnnotation(String name){
        System.out.println("Hi, " + name);
    }
    
    // ʹ��ע�Ⲣʹ��Ĭ�ϲ����ķ���
    @SayHiAnnotation
    public void SayHiAnnotationDefault(String name){
        System.out.println("Hi, " + name);
    }
}
