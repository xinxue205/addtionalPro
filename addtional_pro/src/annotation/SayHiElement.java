/**
 * 
 */
package annotation;

/**
 * 要使用SayHiAnnotation的元素所在类
 * 由于我们定义了只有方法才能使用我们的注解，我们就使用多个方法来进行测试
 * @author wuxinxue
 * @time 2015-7-23 下午5:33:11
 * @copyright sinobest
 */
public class SayHiElement {

    // 普通的方法
    public void SayHiDefault(String name){
        System.out.println("Hi, " + name);
    }
    
    // 使用注解并传入参数的方法
    @SayHiAnnotation(paramValue="Jack")
    public void SayHiAnnotation(String name){
        System.out.println("Hi, " + name);
    }
    
    // 使用注解并使用默认参数的方法
    @SayHiAnnotation
    public void SayHiAnnotationDefault(String name){
        System.out.println("Hi, " + name);
    }
}
