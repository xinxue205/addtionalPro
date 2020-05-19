package exception;

public class Test {

	public static void main(String[] args) {
		try {
			teste(1);
		} catch (TestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void teste(int a ) throws TestException{
		if (a == 0) throw new TestException("出错：不能为0！");
		System.out.println(a);
	}
}

class TestException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7860559787747043515L;
	String msg;
	
	TestException(String msg){
		this.msg = msg;
	}
}