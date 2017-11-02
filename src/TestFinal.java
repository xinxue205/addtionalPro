
public class TestFinal {
	public static void main(String[] args) {
		final Test2 test = new Test2(1,"1");
		System.out.println(test.var);
		test.var="3";
		System.out.println(test.var);
	}
	
	static void test(final String str){
//		str = "123";
		System.out.println(str);
	}
	
	
}

class Test2{
	int id;
	String var;

	/**
	 * @param string
	 */
	public Test2(int id, String var) {
		this.id = id;
		this.var = var;
		// TODO Auto-generated constructor stub
	}
	
}
