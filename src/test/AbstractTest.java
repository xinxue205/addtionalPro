package test;

public class AbstractTest extends Stu{
	public static void main(String[] args) {
		AbstractTest abst = new AbstractTest();
		name = "Shawn";
		abst.study();
		abst.sleep();
	}
	void sleep() {
		System.out.println(name + " is sleeping");
	}
}

abstract class Stu{
	protected static String name;
	void study(){
		System.out.println(name+" is studing");
	}
	abstract void sleep();
}