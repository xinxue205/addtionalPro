package aop.schema;

import java.lang.reflect.Method;

import org.springframework.aop.support.NameMatchMethodPointcut;

/**
 * 定义一个切点，指定对应方法匹配。来供切面来针对方法进行处理<br>
 * 
 * 继承NameMatchMethodPointcut类，来用方法名匹配
 * 
 * @author yanbin
 * 
 */
public class Pointcut extends NameMatchMethodPointcut {

    private static final long serialVersionUID = 3990456017285944475L;

    @SuppressWarnings("rawtypes")
    @Override
    public boolean matches(Method method, Class targetClass) {
        // 设置单个方法匹配
        this.setMappedName("delete");
        // 设置多个方法匹配
        String[] methods = { "delete", "modify" };

        //也可以用“ * ” 来做匹配符号
        // this.setMappedName("get*");

        this.setMappedNames(methods);

        return super.matches(method, targetClass);
    }

}
