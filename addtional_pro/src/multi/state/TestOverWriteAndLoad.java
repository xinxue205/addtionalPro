package multi.state;

public class TestOverWriteAndLoad {
	public static void main(String[] args) {
		BigChair bc = new BigChair();
		bc.hasLegs(4);
		Chair c1 = (Chair) bc;
		c1.hasLegs();
		Chair c2 = new Chair();
		c2.hasLegs();
	}
}

interface Usable{
	void use();//接口的方法范围只能是默认或public
}

class Chair implements Usable{
	public void use(){//实现接口的方法范围必须是public
		System.out.println("Chair is using");
	}
	
	void hasLegs() {
		System.out.println("Chair has Legs");
	}
}

class BigChair extends Chair implements Usable{
	public void use(){
		System.out.println("Bigchair is using");
	}
	
	protected void hasLegs() {//不能用private修饰，范围不能比要重写的方法窄
		System.out.println("BigChair has big legs");
	}
	
	void hasLegs(int count){
		System.out.println("Bigchair(legs:" + count + ") hasLegs");
	}
	
//	int hasLegs(int count){//参数列表决定是否重名重写，返回值不影响
//		System.out.println("Bigchair(legs:" + count + ") hasLegs");
//		return count;
//	}
}
