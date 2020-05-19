package container;

import java.util.*;

public class TestSet {
	public static void main(String[] args) {
		@SuppressWarnings("rawtypes")
		Set<Comparable> s = new HashSet<Comparable>();
		s.add("hello");
		s.add("world");
		s.add(new Name("f1","l1"));
		s.add("hello");
		System.out.println(s);
		
		Set<String> sn = new HashSet<String>();
		sn.add("world, ]");
		sn.add("hello");
		System.out.println(sn);
		sn.retainAll(s);
		System.out.println(sn);
	}
}
