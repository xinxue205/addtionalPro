package multi.state;

public class TestOverWriteAndLoad {
	public static void main(String[] args) {
		BigChair bc = new BigChair();
		bc.use();
		Chair c1 = (Chair) bc;
		c1.use();
		Chair c2 = new Chair();
		c2.use();
	}
}

interface Usable{
	void use();
	void put();
}

class Chair{
	void use(){
		System.out.println("Chair is using");
	}
}

class BigChair extends Chair implements Usable{
	public void use(){
		System.out.println("Bigchair is using");
	}
	
	String use(String name){
		System.out.println("Bigchair" + name + " is using");
		return name;
	}

	public void put() {
		// TODO Auto-generated method stub
		
	}
}
