package regEx;

public class Test1 {
	public static void main(String[] args) {
		String s1=" s434df��d666f ";
		System.out.println(s1.trim());;
		//String s2="sdfsdf";
		p(s1.matches("......"));
		p(s1.replaceAll("\\d", "-"));
		p("abc".matches("[a-z]{3}"));

	}
	public static void p(Object o){
		System.out.println(o);
	}
	
}
