package loaderAndReflection;

class Person{
	private String name;
	private int age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public String toString() {
		return "Ãû×Ö£º "+name+" £¬ÄêÁä£º"+age;
	}
}

public class Demo2 {
	public static void main(String[] args) {
		Class<?> c = null;
		try {
			c = Class.forName("loaderAndReflection.Person");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Person p = null;
		try {
			p = (Person) c.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.setAge(18);
		p.setName("pp");
		System.out.println(p);
	}
}
