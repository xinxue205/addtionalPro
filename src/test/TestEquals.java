package test;

public class TestEquals {
	public static void main(String[] args) {
		Cat c1 = new Cat("mimi",1,2,3);
		Cat c2 = new Cat("gigi",1,2,3);
		System.out.println(c1.name);
		System.out.println(c2.name);	
		System.out.println(c1.equals(c2));
	}
}

class Cat extends Animal{
	int color;
	int height, weight;
	Cat(String n, int color, int height, int weight){
		super(n);
		this.color = color;
		this.height = height;
		this.weight = weight;
	}
	
	public boolean equals(Object obj){
		if (obj == null) {return false;}
		else {
			if (obj instanceof Cat){
				Cat c = (Cat)obj;
				if (this.color == c.color && this.height == c.height && this.weight == c.weight ) {return true;}
			}
		return false;	
		}
	}

}
