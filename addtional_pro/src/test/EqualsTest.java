package test;

public class EqualsTest {
	public static void main(String[] args) {
		/*Dog d1 = new Dog("D", "d");
		Dog d2 = new Dog("D", "d");
		System.out.println(d1.name==d2.name);
		System.out.println(d1.name.equals(d2.name));
		System.out.println(d1==d2);
		System.out.println(d1.equals(d2));*/
		
		/*String s1 = "abc";
		String s2 = new String("abc");
		System.out.println(s1.equals(s2));
		System.out.println(s1==s2);*/

		
		Thing t1 = new Thing("a",2);
		Thing t2 = new Thing("a",2);
		System.out.println(t1.hashCode());
		System.out.println(t2.hashCode());
		System.out.println(t1.equals(t2));
		System.out.println(t1==t2);
		if(t1.number==t2.number){
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
	}
}

class Thing{
	String name;
	int number;
	Thing(String name, int number){
		this.name=name;
		this.number=number;
	}
}
