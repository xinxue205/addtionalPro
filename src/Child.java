

public class Child extends Father implements ISayable{
	public String child_str;
	public String father_str;

	public Child() {
		//super();
		System.out.println("chidl init,,,child_str = " + child_str);
	}

	
	public static void main(String[] args) {
		Child a = new Child();
		a.father_str="123";
		
		Father f = (Father) a;
		System.out.println(f.father_str);
		System.out.println(a.father_str);

//		System.out.println(Child.class.isAssignableFrom(a.getClass()));
//		System.out.println(Father.class.isAssignableFrom(a.getClass()));
//		System.out.println( a instanceof Child);
//		System.out.println( a instanceof Father);
//		System.out.println(ISayable.class.isAssignableFrom(a.getClass()));
	}


	@Override
	public void say() {
		System.out.println("i am the child!");
	}
}

class Inition {

	public static void init(Father father) {
	
		if(father instanceof Child) {
			Child child = (Child) father;
			child.child_str = "child";
		}
		
		father.father_str = "father";
	}
}

class Father{
	public String father_str;
	public Father() {
		father_str = "father";
		System.out.println("fater init,,,father_str = " + father_str);
	}
}

interface ISayable{
	void say();
}