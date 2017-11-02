package test;

public class TestEvaluate {
	 public static void main(String[] args) {
		int a=2,b=5,c=3;
		a+=--b+c;
		//a+=b--+c;
		System.out.println(b);
		c-=++b+a;
		System.out.println(a+"  "+b+"  "+c);
	}
}
