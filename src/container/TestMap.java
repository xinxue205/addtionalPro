package container;

import java.util.*;

public class TestMap {
	public static void main(String[] args) {
		Map<String, Integer> m1 = new HashMap<String, Integer>();
		Map<String, Integer> m2 = new HashMap<String, Integer>();
		//m1.put("one", new Integer(1));
		m1.put("one", 1);
		//m1.put("two", new Integer(2));
		m1.put("two", 2);
		//m2.put("A", new Integer(1));
		m2.put("A", 1);
		for (Integer i : m1.values()) {
			System.out.println(i);
		}
//		System.out.println(m1.size());
//		System.out.println(m1.containsKey("one"));
//		if(m1.containsKey("two")){
//			int i = (Integer)m1.get("two");
//			//int i = ((Integer)m1.get("two")).intValue();
//			System.out.println(i);
//		}
		
	}
}
