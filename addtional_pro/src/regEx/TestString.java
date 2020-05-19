package regEx;

public class TestString {
	public static void main(String[] args) {
		String s1=" s434dfŒ“d666f ";
		System.out.println(s1.trim());;
		//String s2="sdfsdf";
		p(s1.matches("......"));
		p(s1.replaceAll("\\d", "-"));
		p("abc".matches("[a-z]{3}"));
		p("123".matches("^[0-9]{4,8}$"));
	}
	public static void p(Object o){
		System.out.println(o);
	}
	
}
