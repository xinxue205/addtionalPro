package container;

import java.util.*;

public class TestList {
	public static void main(String[] args) {
		List l = new LinkedList();
		for (int i=0; i<9; i++){
			l.add("a"+i);
		}
		System.out.println(l);
		Collections.shuffle(l);
		System.out.println(l);
		Collections.reverse(l);
		System.out.println(l);
		Collections.sort(l);
		System.out.println(l);
		System.out.println(Collections.binarySearch(l, "a3"));
		
		List l1 = new LinkedList();
		l1.add(new Name("f4","l4"));
		l1.add(new Name("f2","l2"));
		l1.add(new Name("f3","l3"));
		System.out.println(l1);
		Collections.sort(l1);
		System.out.println(l1);	
		
		System.out.println("\n\"sdfs\"");
		System.out.println(Long.parseLong("123"));
		System.out.println(Long.valueOf(123));
		System.out.println(Long.valueOf(123).floatValue());
	}
}
