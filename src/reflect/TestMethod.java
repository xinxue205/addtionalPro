/**
 * 
 */
package reflect;

import java.lang.reflect.Method;

/**
 * Method≤‚ ‘¿‡
 * @author wuxinxue
 * @time 2015-6-18 …œŒÁ9:08:55
 * @copyright hnisi
 */
public class TestMethod {
	public static void main(String[] args) throws Exception {
		Class<?> c = Class.forName("reflect.Entity");
		Entity e = (Entity) c.newInstance();
		
		Method method = c.getMethod("set", new Class[]{int.class, String.class});
		method.invoke(e, new Object[]{1,"xiao"});
		
		method = c.getMethod("get", null);
		System.out.println(method.invoke(e, null));;
	}
}

class Entity{
	private int i;
	private String str;
	public void set(int i, String str){
		this.i=i;
		this.str=str;
		
	}
	public String get(){
		return str+i;
	}
}
