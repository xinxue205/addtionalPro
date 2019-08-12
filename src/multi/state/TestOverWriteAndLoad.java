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
	void use();//�ӿڵķ�����Χֻ����Ĭ�ϻ�public
}

class Chair implements Usable{
	public void use(){//ʵ�ֽӿڵķ�����Χ������public
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
	
	protected void hasLegs() {//������private���Σ���Χ���ܱ�Ҫ��д�ķ���խ
		System.out.println("BigChair has big legs");
	}
	
	void hasLegs(int count){
		System.out.println("Bigchair(legs:" + count + ") hasLegs");
	}
	
//	int hasLegs(int count){//�����б�����Ƿ�������д������ֵ��Ӱ��
//		System.out.println("Bigchair(legs:" + count + ") hasLegs");
//		return count;
//	}
}
