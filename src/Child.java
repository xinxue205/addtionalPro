

public class Child extends Father{
	public String child_str;
	public Child() {
		//super();
		System.out.println("chidl init,,,child_str = " + child_str);
	}

	public static void main(String[] args) {
		new Child();
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
		Inition.init(this);
		System.out.println("fater init,,,father_str = " + father_str);
	}
}