/**
 * 
 */
package annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wuxinxue
 * @time 2015-7-23 下午5:34:11
 * @copyright sinobest
 */
public class AnnotionOperator {
    public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        SayHiElement element = new SayHiElement(); // 初始化一个实例，用于方法调用
        element.getClass().getAnnotations();
        Method[] methods = SayHiElement.class.getDeclaredMethods(); // 获得所有方法
        
        for (Method method : methods) {
            SayHiAnnotation annotationTmp = null;
            if((annotationTmp = method.getAnnotation(SayHiAnnotation.class))!=null) // 检测是否使用了我们的注解
                method.invoke(element,annotationTmp.paramValue()); // 如果使用了我们的注解，我们就把注解里的"paramValue"参数值作为方法参数来调用方法
            else
                method.invoke(element, "Rose"); // 如果没有使用我们的注解，我们就需要使用普通的方式来调用方法了
        }
    }
}
