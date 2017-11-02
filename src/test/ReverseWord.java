package test;
//import java.util.Collections;
//import java.util.List;

public class ReverseWord {
	public static void main(String[] args) {
		String s="here";
		System.out.println(reverse1(s));;
		//System.out.println(reverse2(s));;
	}
	
	public static StringBuffer reverse1(String s){
		StringBuffer sb = new StringBuffer();
		for(int i=s.length()-1; i>=0;i--){
			char c = s.charAt(i);
			sb.append(c);
		}
		return sb;
	}
	
	public static StringBuffer reverse2(String s){
		char[] list;
		list = s.toCharArray();
		StringBuffer sb = new StringBuffer();
		char c;
		for(int i=list.length-1; i>=0;i--){
			c = list[i];
			sb.append(c);
		}
		return sb;
	}

}
