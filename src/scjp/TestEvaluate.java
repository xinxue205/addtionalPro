package scjp;

public class TestEvaluate {
	public static void main(String[] args) {
		int a=0;
		int b[]=new int[5];
		int c=3;
		b[a]=a=c;  //��b[a]=a; a=c�кܴ�����
		System.out.println(a);  //3
		System.out.println(b[a]);  //0
		System.out.println(b[0]);  //3
		System.out.println(b[3]);	//0
	}
}
