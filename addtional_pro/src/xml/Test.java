package xml;

import loaderAndReflection.CommonLoader;

/*��ȡxml�ļ�����
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