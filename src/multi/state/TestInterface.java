package multi.state;
interface Singer {
	public void sing();
	public void sleep();
}

interface Painter {
	public void paint();
	public void eat();
}

class Student implements Singer {
	private String name;
	Student (String name) {
		this.name = name;
	}
	public void study(){
		System.out.println(this.name + " is studing");
	}
	public void sing(){
		System.out.println(this.name + " is singing");
	}
	public void sleep(){
		System.out.println(this.name + " is sleeping");
	}
}

class Teacher implements Singer,Painter {
	private String name;
	Teacher (String name) {
		this.name = name;
	}
	public void teach(){
		System.out.println(this.name+" is teaching");
	}
	public void sing(){
		System.out.println(this.name+" is singing");
	}
	public void sleep(){
		System.out.println(this.name+" is sleeping");
	}
	public void paint(){
		System.out.println(this.name+" is painting");
	}
	public void eat(){
		System.out.println(this.name+" is eating");
	}
}

public class TestInterface {
	public static void main(String[] args) {
		/*Singer s1 = new Student("Stu");
		s1.sing();
		s1.sleep();
		Singer s2 = new Teacher("Tea");
		s2.sing();
		s2.sleep();
		Painter p1 = (Painter)s2;
		p1.paint();
		p1.eat();*/
		
		GoodSinger ps = new PopularStar();
		ps.sing();
	}
}

class GoodSinger implements Singer {
	public void sleep() {
	}
	public void sing() {
		// TODO Auto-generated method stub
		System.out.println("Sings good!");
	}
}
class PopularStar extends GoodSinger{
	public void sing() {
		// TODO Auto-generated method stub
		System.out.println("I'm PopularStar!");
		super.sing();
	}
}