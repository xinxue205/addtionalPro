package scjp;

public class TestString {
	public static void main(String[] args) {
		String a = "JAVA";
		String b = "JAVA";
		String c = new String("JAVA");
		String d = "JA";
		String e = "VA";
		String f = "JA"+"VA";
		String g = d+e;
		String h = c;
		System.out.println(a==b); //true
		System.out.println(a==c); //false
		System.out.println(a==f); //true
		System.out.println(a==g); //false
		System.out.println(h==c); //true
	}
}
