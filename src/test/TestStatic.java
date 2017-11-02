package test;

public class TestStatic{

	private static int total = 200;
	static{
	  total = 100;
	  System.out.println("staticÓï¾ä¿é");
	}	
	public static void main(String[] args){
	  System.out.println(TestStatic.total);
	  System.out.println(TestStatic.total);
	}
}

