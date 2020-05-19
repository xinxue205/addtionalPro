package xml;

import loaderAndReflection.CommonLoader;

/*获取xml文件属性
 * 
 * */

public class Test {
	public static void main(String[] args) throws Exception {
		ClassGetter cg = new ClassGetter();
		CommonLoader t = (CommonLoader) cg.getClassInstance("src/classPath.xml", "classLoder");
		t.sayHello();
	}
	
	public void sayHello(){
		System.out.println("Hello!!!");
	}
}