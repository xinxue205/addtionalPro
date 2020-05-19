package regEx;

import java.util.regex.*;

public class TestPattern {
	public static void main(String[] args) {
		p("safsadf.json".matches("*.json"));
		p("a".matches("a+"));
		
		String s1="s434dfŒ“d666f";
		//String s2="sdfsdf";
		p(s1.matches("......"));
		p(s1.replaceAll("\\d", "-"));
		Pattern p = Pattern.compile("[a-z]{3}");
		Matcher m = p.matcher(s1);
		p(m.matches());	
		p("abc".matches("[a-z]{3}"));
		p("13111121121".matches("[0-9]{11}"));

	}
	public static void p(Object o){
		System.out.println(o);
	}
	
}
