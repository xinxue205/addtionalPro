package aop.schema;

/**
 * 代理类接口，也是业务类接口<br>
 * 
 * 利用接口的方式，spring aop 将默认通过jdk 动态代理来实现代理类<br>
 * 不利用接口，则spring aop 将通过cglib 来实现代理类
 * 
 * @author yanbin
 * 
 */
public interface IBaseBusiness {

    /**
     * 用作代理的切入点方法
     * 
     * @param obj
     * @return
     */
    public String delete(String obj);

    /**
     * 这方法不被切面切
     * 
     * @param obj
     * @return
     */
    public String add(String obj);

    /**
     * 这方法切不切呢？可以设置
     * 
     * @param obj
     * @return
     */
    public String modify(String obj);

}
