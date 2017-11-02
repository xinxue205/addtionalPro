package test;

class Animal{
	protected String name;
	Animal(String name){
		this.name = name;
	}
}

class Dog extends Animal{
	String furColor;
	Dog (String n, String furColor){
		super(n);
		this.furColor = furColor;
	}
}

class Bird extends Animal{
	String featherColor;
	Bird (String n, String featherColor){
		super(n);
		this.featherColor = featherColor;
	}
}

class Mouse extends Animal{
	int teethNumber;
	Mouse(String n, int teethNumber){
		super(n);
		this.teethNumber = teethNumber;
	}
}

public class TestCasting {
	public static void main(String[] args) {
		TestCasting tc = new TestCasting();
		Animal a = new Animal("Dirk");
		Dog d = new Dog("bigblack", "black");
		Bird b = new Bird("bluebird", "blue");
		Mouse m = new Mouse("eightteeth",5);
		Cat c = new Cat("gigi",1,2,3);
		tc.f(a);
		tc.f(b);
		tc.f(d);
		tc.f(m);
		tc.f(c);
	}
	public void f(Animal n) {
		//System.out.println(n.name);		
		if (n instanceof Dog) {
			Dog d = (Dog)n; 
			System.out.println(d.name+" "+d.furColor);
		}
		else if (n instanceof Bird) {
			Bird b = (Bird)n; 
			System.out.println(b.name+" "+b.featherColor);
		}
		else if (n instanceof Mouse) {
			Mouse m = (Mouse)n; 
			System.out.println(m.name+" "+m.teethNumber);
		}
		else if(n instanceof Cat) {
			Cat c = (Cat)n; 
			System.out.println(c.name+" "+c.height);
		}
		else if(n instanceof Animal) {
			System.out.println(n.name);
		}
	}
}
