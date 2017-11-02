package regEx;

import java.util.regex.*;

public class Test2 {
	public static void main(String[] args) {
		//System.out.println("a0090023456".matches("[0-9]{11}"));
		System.out.println("13000000000".matches("1[0-9]{10}"));
	}
}
